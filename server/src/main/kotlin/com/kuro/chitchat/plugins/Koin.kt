package com.kuro.chitchat.plugins

import com.kuro.chitchat.database.server.di.databaseServerModule
import com.kuro.chitchat.di.koinModule
import io.ktor.server.application.Application
import org.koin.core.context.startKoin

fun Application.configureKoin() {
    startKoin {
        modules(databaseServerModule, koinModule)
    }
}