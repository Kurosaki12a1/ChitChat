package com.kuro.chitchat.auth_core.di

import android.content.Context
import androidx.startup.Initializer
import com.kuro.chitchat.auth_core.KMPAuthInternalApi
import org.koin.dsl.module

internal lateinit var applicationContext: Context
    private set

internal class ContextInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        applicationContext = context.applicationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}


@KMPAuthInternalApi
actual fun isAndroidPlatform(): Boolean = true
internal actual val platformModuleAuth = module {
    single { applicationContext }
}


