package com.kuro.chitchat.auth_google.di

import com.kuro.chitchat.auth_google.GoogleAuthProvider
import com.kuro.chitchat.auth_google.GoogleAuthProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module


internal actual val googleAuthPlatformModule: Module = module {
    factoryOf(::GoogleAuthProviderImpl) bind GoogleAuthProvider::class
}