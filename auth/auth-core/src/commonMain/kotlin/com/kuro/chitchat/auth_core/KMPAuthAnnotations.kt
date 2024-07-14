package com.kuro.chitchat.auth_core

/**
 * KMPAuthInternal Annotation class that limits access for internal usage
 */
@RequiresOptIn(
    message = "This is internal API for KMPAuth. This shouldn't be used outside of KMPAuth API",
    level = RequiresOptIn.Level.ERROR
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY
)
annotation class KMPAuthInternalApi