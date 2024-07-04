package com.kuro.chitchat

import android.app.Application
import com.kuro.chitchat.di.appModule
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import utils.CLIENT_ID

class ChitChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@ChitChatApplication)
            modules(appModule)
        }
    }
}