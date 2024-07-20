plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "client"
            isStatic = true
        }
    }

    jvm()

    sourceSets {
        sourceSets.commonMain {
            kotlin.srcDir("build/generated/ksp/metadata")
        }

        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Koin core features
            implementation(libs.koin.core)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", libs.androidx.room.compiler)
}


android {
    namespace = "com.kuro.chitchat.database.client"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata" ) {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
