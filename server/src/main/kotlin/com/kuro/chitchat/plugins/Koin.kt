package com.kuro.chitchat.plugins

import com.kuro.chitchat.di.koinModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    startKoin {
        modules(koinModule)
    }
}