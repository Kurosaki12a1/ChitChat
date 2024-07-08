import androidx.datastore.core.DataStore
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.data_source.local.AppDatabase
import data.data_source.local.dao.ChatRoomDao
import data.data_source.local.dbFileName
import di.createDataStore
import org.koin.dsl.module
import java.io.File

actual fun platformModule() = module {
    // single { Java.create() }
    single { dataStore() }
    single<AppDatabase> { createRoomDatabase() }
}

fun dataStore(): DataStore<androidx.datastore.preferences.core.Preferences> =
    createDataStore(
        producePath = { "chitchat.preferences_pb" }
    )

fun createRoomDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), dbFileName)
    return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .build()
}