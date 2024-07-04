package domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveSignedInState(signedIn: Boolean)
    fun readSignedInState(): Flow<Boolean>

    suspend fun saveConfirmedState(isConfirmed: Boolean, id: String)
    fun readConfirmedState(id: String): Flow<Boolean>
}