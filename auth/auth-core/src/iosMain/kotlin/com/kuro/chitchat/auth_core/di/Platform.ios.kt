package com.kuro.chitchat.auth_core.di

import com.kuro.chitchat.auth_core.KMPAuthInternalApi
import org.koin.dsl.module


@KMPAuthInternalApi
internal actual fun isAndroidPlatform(): Boolean = false

internal actual val platformModuleAuth = module {

}