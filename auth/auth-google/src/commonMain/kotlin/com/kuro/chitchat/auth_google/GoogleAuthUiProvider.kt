package com.kuro.chitchat.auth_google

/**
 * Provider class for Google Authentication UI part. a.k.a [signIn]
 */
interface GoogleAuthUiProvider {

    /**
     * Opens Sign In with Google UI, and returns [GoogleUser]
     * if sign-in was successful, otherwise, null
     * @return returns GoogleUser or null(if sign-in was not successful)
     */
    suspend fun signIn(): GoogleUser?
}