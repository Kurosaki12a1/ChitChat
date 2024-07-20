package com.kuro.chitchat.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.litote.kmongo.serialization.LocalDateTimeSerializer
import utils.KotlinLocalDateTimeSerializer

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            ignoreUnknownKeys = true
            prettyPrint = true
            serializersModule = SerializersModule {
                contextual(LocalDateTime::class, KotlinLocalDateTimeSerializer)
            }
        })
    }
}
