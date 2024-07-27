package data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import domain.repository.DataStoreOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import utils.PREFERENCES_SIGNED_IN_ID_KEY
import utils.PREFERENCES_SIGNED_IN_KEY

/**
 * Implementation of DataStoreOperations for managing signed-in state and ID.
 *
 * @property dataStore The DataStore used for storing preferences.
 */
class DataStoreOperationsImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperations, KoinComponent {
    private object PreferencesKey {
        val signedInKey = booleanPreferencesKey(name = PREFERENCES_SIGNED_IN_KEY)
        val signedInIdKey = stringPreferencesKey(name = PREFERENCES_SIGNED_IN_ID_KEY)
    }

    /**
     * Saves the signed-in state.
     *
     * @param signedIn The signed-in state to be saved.
     */
    override suspend fun saveSignedInState(signedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.signedInKey] = signedIn
        }
    }

    /**
     * Reads the signed-in state.
     *
     * @return A Flow emitting the signed-in state.
     */
    override fun readSignedInState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val signedInState = preferences[PreferencesKey.signedInKey] ?: false
                signedInState
            }.flowOn(Dispatchers.IO)
    }

    /**
     * Saves the signed-in ID.
     *
     * @param id The signed-in ID to be saved.
     */
    override suspend fun saveSignedInId(id: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.signedInIdKey] = id
        }
    }

    /**
     * Retrieves the current signed-in ID.
     *
     * @return A Flow emitting the current signed-in ID.
     */
    override fun getCurrentSignedIn(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKey.signedInIdKey] ?: ""
            }.flowOn(Dispatchers.IO)
    }
}