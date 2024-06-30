package com.kuro.chitchat.plugins

import com.kuro.chitchat.di.koinModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(koinModule)
    }
}