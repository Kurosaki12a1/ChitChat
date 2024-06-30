package com.kuro.chitchat

import android.app.Application
import com.kuro.chitchat.di.appModule
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class ChitChatApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@ChitChatApplication)
            modules(appModule)
        }
    }
}