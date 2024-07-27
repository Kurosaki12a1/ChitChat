package domain.models

import io.ktor.http.Cookie
import io.ktor.util.date.GMTDate
import kotlinx.serialization.Serializable

/**
 * Data class representing the serializable form of a Cookie.
 */
@Serializable
data class CookieData(
    val name: String,
    val value: String,
    val expires: Long? = null,
    val domain: String? = null,
    val path: String? = null,
    val secure: Boolean = false,
    val httpOnly: Boolean = false
) {
    /**
     * Converts a CookieData object to a Cookie object.
     *
     * @return A Cookie object with the same properties.
     */
    fun toCookie(): Cookie {
        return Cookie(
            name = name,
            value = value,
            expires = expires?.let { GMTDate(it) },
            domain = domain,
            path = path,
            secure = secure,
            httpOnly = httpOnly
        )
    }


    companion object {
        /**
         * Creates a CookieData object from a Cookie object.
         *
         * @param cookie The Cookie object to be converted.
         * @return A CookieData object with the same properties.
         */
        fun fromCookie(cookie: Cookie): CookieData {
            return CookieData(
                name = cookie.name,
                value = cookie.value,
                expires = cookie.expires?.timestamp,
                domain = cookie.domain,
                path = cookie.path,
                secure = cookie.secure,
                httpOnly = cookie.httpOnly
            )
        }
    }
}