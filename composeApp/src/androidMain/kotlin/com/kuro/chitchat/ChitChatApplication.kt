package com.kuro.chitchat

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.kuro.chitchat.di.appModule
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.binds
import org.koin.dsl.module


class ChitChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (true) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build()
            )
        }
        initKoin {
            androidLogger()
            androidContext(this@ChitChatApplication)
            modules(appModule)
        }
    }
}