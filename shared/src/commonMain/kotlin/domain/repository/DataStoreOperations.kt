package domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveSignedInState(signedIn: Boolean)
    fun readSignedInState(): Flow<Boolean>

    suspend fun saveSignedInId(id: String)
    fun getCurrentSignedIn(): Flow<String>
}