package com.kuro.chitchat.database.client

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kuro.chitchat.database.client.data.AppDatabase
import com.kuro.chitchat.database.client.data.dbFileName
import com.kuro.chitchat.database.client.data.instantiateImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

actual fun databasePlatformModule() = module {
    single<AppDatabase> { createRoomDatabase() }
}

private fun fileDirectory(): String {
    val documentDirectory: NSURL = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}


fun createRoomDatabase(): AppDatabase {
    val dbFile = "${fileDirectory()}/$dbFileName"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

