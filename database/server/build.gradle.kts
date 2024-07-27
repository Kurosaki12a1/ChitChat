plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    application
}

dependencies {
    // KMongo
    implementation(libs.kmongo.async)
    implementation(libs.kmongo.coroutine.serialization)
    implementation(libs.bson.kotlinx)
    implementation(libs.kotlinx.serialization.core)

    implementation(libs.kotlinx.datetime)

    // Koin core features
    implementation(libs.koin.core)
}