package com.kuro.chitchat.database.client

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kuro.chitchat.database.client.data.AppDatabase
import com.kuro.chitchat.database.client.data.dbFileName
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun databasePlatformModule() = module {
    single<AppDatabase> { createRoomDatabase(get()) }
}

fun createRoomDatabase(ctx: Context): AppDatabase {
    val dbFile = ctx.getDatabasePath(dbFileName)
    return Room.databaseBuilder<AppDatabase>(ctx, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
