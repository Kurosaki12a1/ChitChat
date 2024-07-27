import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import di.createDataStore
import io.ktor.client.engine.darwin.Darwin
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.AuthViewModel
import viewmodel.MoreViewModel

actual fun platformModule() = module {
    single { Darwin.create() }
    single { dataStore() }

    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { MoreViewModel(get(), get()) }
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