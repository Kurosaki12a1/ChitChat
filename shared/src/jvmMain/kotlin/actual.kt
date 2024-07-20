import androidx.datastore.core.DataStore
import di.createDataStore
import org.koin.dsl.module


actual fun platformModule() = module {
    // single { Java.create() }
    single { dataStore() }
}

fun dataStore(): DataStore<androidx.datastore.preferences.core.Preferences> =
    createDataStore(
        producePath = { "chitchat.preferences_pb" }
    )