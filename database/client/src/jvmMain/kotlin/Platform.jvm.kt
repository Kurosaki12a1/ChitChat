package com.kuro.chitchat.database.client

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kuro.chitchat.database.client.data.AppDatabase
import com.kuro.chitchat.database.client.data.dbFileName
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual fun databasePlatformModule(): Module = module {
    single<AppDatabase> { createRoomDatabase() }
}

fun createRoomDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), dbFileName)
    return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .build()
}
