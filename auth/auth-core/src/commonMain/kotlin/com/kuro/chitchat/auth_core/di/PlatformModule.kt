package com.kuro.chitchat.auth_core.di

import com.kuro.chitchat.auth_core.KMPAuthInternalApi
import org.koin.core.module.Module

@KMPAuthInternalApi
internal expect fun isAndroidPlatform(): Boolean

internal expect val platformModuleAuth: Module