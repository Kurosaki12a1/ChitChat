import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import di.createDataStore
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module
import utils.CLIENT_ID

actual fun platformModule() = module {
    GoogleAuthProvider.create(credentials = GoogleAuthCredentials(CLIENT_ID))
    single { OkHttp.create() }
    single { dataStore(get()) }
}

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve("chitchat.preferences_pb").absolutePath }
    )