import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import di.createDataStore
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module
import utils.CLIENT_ID

actual fun platformModule() = module {
    GoogleAuthProvider.create(credentials = GoogleAuthCredentials(CLIENT_ID))
    single { Darwin.create() }
    single { dataStore() }
}

fun dataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory: NSURL = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/chitchat.preferences_pb"
    }
)
