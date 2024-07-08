import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import data.data_source.local.AppDatabase
import data.data_source.local.dbFileName
import di.createDataStore
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import utils.CLIENT_ID

actual fun platformModule() = module {
    GoogleAuthProvider.create(credentials = GoogleAuthCredentials(CLIENT_ID))
    single { OkHttp.create() }
    single { dataStore(get()) }
    single<AppDatabase> { createRoomDatabase(get()) }
}

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve("chitchat.preferences_pb").absolutePath }
    )

fun createRoomDatabase(ctx: Context): AppDatabase {
    val dbFile = ctx.getDatabasePath(dbFileName)
    return Room.databaseBuilder<AppDatabase>(ctx, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
