package data.local.cookie

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import domain.model.CookieData
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * CookieStorageManager is a class that manages the storage and retrieval of cookies using DataStore.
 * It implements the CookiesStorage interface from Ktor client.
 *
 * @property dataStore The DataStore<Preferences> instance used for storing cookies.
 * @property myDomain The domain for which cookies should be stored and retrieved.
 * More details can check class io.ktor.client.plugins.cookies.AcceptAllCookieStorage
 */

class CookieStorageManager(
    private val dataStore: DataStore<Preferences>,
    private val myDomain: String?
) : CookiesStorage {

    /**
     * Adds a cookie to the DataStore.
     *
     * @param requestUrl The URL of the request for which the cookie is being added.
     * @param cookie The Cookie object that needs to be stored.
     */
    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        // Determine the domain to store the cookie under
        val domain = myDomain ?: requestUrl.host
        // Convert the Cookie object to a serializable CookieData object.
        val cookieData = CookieData.fromCookie(cookie)
        // Serialize the CookieData object to a JSON string.
        val cookieString = Json.encodeToString<CookieData>(cookieData)

        withContext(Dispatchers.IO) {
            // Create a preferences key for the domain.
            val key = stringSetPreferencesKey(domain)
            dataStore.edit { preferences ->
                // Get existing cookies or create a new set if none exist.
                val cookies = preferences[key]?.toMutableSet() ?: mutableSetOf()
                // Remove any existing cookie with the same name and domain.
                cookies.removeAll { existCookie ->
                    val existCookieData = Json.decodeFromString<CookieData>(existCookie)
                    existCookieData.name == cookie.name && existCookieData.domain == domain
                }
                cookies.add(cookieString) // Add the new cookie to the set.
                preferences[key] = cookies // Save the updated set of cookies to DataStore.
            }
        }
    }

    /**
     * Closes the cookie storage. This implementation does not require any specific action to close.
     */
    override fun close() {}

    /**
     * Retrieves a list of cookies from the DataStore for the specified URL.
     * This function also cleans up expired cookies.
     *
     * @param requestUrl The URL for which cookies need to be retrieved.
     * @return A list of valid Cookie objects.
     */
    override suspend fun get(requestUrl: Url): List<Cookie> {
        val domain = myDomain ?: requestUrl.host
        val cookies: Set<String> =
            dataStore.data.first()[stringSetPreferencesKey(domain)] ?: emptySet()

        // Filter out expired cookies and convert valid ones back to Cookie objects.
        val validCookies = cookies.mapNotNull {
            val cookieData = Json.decodeFromString<CookieData>(it)
            val now = Clock.System.now().toEpochMilliseconds()
            if (cookieData.expires != null && cookieData.expires < now) {
                // If the cookie has expired, return null to filter it out.
                null
            } else {
                // Convert the CookieData object back to a Cookie object.
                cookieData.toCookie()
            }
        }

        // Cleanup expired cookies by saving only the valid cookies back to DataStore.
        dataStore.edit { preferences ->
            val newCookies =
                validCookies.map { Json.encodeToString(CookieData.fromCookie(it)) }.toMutableSet()
            preferences[stringSetPreferencesKey(domain)] = newCookies
        }
        // Return the list of valid cookies.
        return cookies.map { Json.decodeFromString<CookieData>(it).toCookie() }
    }
}