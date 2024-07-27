package com.kuro.chitchat.auth_google

import androidx.compose.runtime.Composable
import com.kuro.chitchat.auth_core.KMPAuthInternalApi
import com.kuro.chitchat.auth_core.di.KMPKoinComponent
import com.kuro.chitchat.auth_core.di.LibDependencyInitializer
import com.kuro.chitchat.auth_google.di.googleAuthModule
import org.koin.core.component.get

/**
 * Google Auth Provider class
 */

interface GoogleAuthProvider {

    companion object {
        /**
         * Creates new [GoogleAuthProvider] class instance
         * @param credentials [GoogleAuthCredentials] instance.
         * @return returns [GoogleAuthProvider]
         */
        fun create(credentials: GoogleAuthCredentials): GoogleAuthProvider {
            return GoogleAuthProviderImpl.create(credentials)
        }

        internal fun get(): GoogleAuthProvider {
            return GoogleAuthProviderImpl.get()
        }
    }

    /**
     * Returns [GoogleAuthUiProvider] that can be used in [Composable] function.
     * @return [GoogleAuthUiProvider]
     */
    @Composable
    fun getUiProvider(): GoogleAuthUiProvider

    /**
     * Signs out user and clears credentials.
     * This function can be called also from data layer. It is not necessary
     * to call #signOut function only from UI layer
     */
    suspend fun signOut()

    @OptIn(KMPAuthInternalApi::class)
    private object GoogleAuthProviderImpl : KMPKoinComponent() {
        fun create(credentials: GoogleAuthCredentials): GoogleAuthProvider {
            LibDependencyInitializer.initialize(googleAuthModule(credentials = credentials))
            return (this as KMPKoinComponent).get()
        }

        fun get(): GoogleAuthProvider {
            try {
                return (this as KMPKoinComponent).get()
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Make sure you invoked GoogleAuthProvider #create method with providing credentials")
            }

        }

    }
}
