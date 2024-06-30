plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
    application
}

group = "com.kuro.chitchat"
version = "1.0.0"
application {
    mainClass.set("com.kuro.chitchat.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

sqldelight {
    databases {
        create("ChitChatDatabase") {
            packageName = "com.kuro.chitchat"
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.2")
        }
    }
}

dependencies {
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

    // postgresql
    implementation(libs.postgresql)
    implementation(libs.hikaricp)
    implementation(libs.jdbc.driver)

    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.cors)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)

    // KMongo
 /*   implementation("org.litote.kmongo:kmongo-async:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")
*/
    // Koin core features
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    // Google Client API Library
    implementation(libs.google.api.client)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}