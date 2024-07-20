plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "com.kuro.chitchat"
version = "1.0.0"

application {
    mainClass.set("com.kuro.chitchat.ApplicationKt")
    applicationDefaultJvmArgs =
        listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(project(":database:server"))
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.call.logging.jvm)
    implementation(libs.logback)

    // Content Negotiation
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ktor.server.content.negotiation.jvm)

    // Sessions
    implementation(libs.ktor.server.sessions.jvm)

    // Auth
    implementation(libs.ktor.server.auth.jvm)

    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.cors)

    // KMongo
    implementation(libs.kmongo.async)
    implementation(libs.kmongo.coroutine.serialization)
    implementation(libs.bson.kotlinx)
    implementation(libs.kotlinx.serialization.core)

    // Koin core features
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    // Google Client API Library
    implementation(libs.google.api.client)

    // Websocket
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.status.pages)


    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}