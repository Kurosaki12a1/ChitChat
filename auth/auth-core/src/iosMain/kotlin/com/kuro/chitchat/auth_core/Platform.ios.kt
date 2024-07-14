package com.kuro.chitchat.auth_core

import org.koin.dsl.module


@KMPAuthInternalApi
internal actual fun isAndroidPlatform(): Boolean = false

internal actual val platformModuleAuth = module {

}