import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import di.createDataStore
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.AddChatViewModel
import viewmodel.AuthViewModel
import viewmodel.ChatRoomViewModel
import viewmodel.ChatViewModel
import viewmodel.MoreViewModel

actual fun platformModule() = module {
    single { OkHttp.create() }
    single { dataStore(get()) }

    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { MoreViewModel(get(), get()) }
    viewModel { ChatViewModel(get(), get()) }
    viewModel { AddChatViewModel(get(), get(), get()) }
    viewModel { ChatRoomViewModel(get(), get(), get()) }
}

fun dataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve("chitchat.preferences_pb").absolutePath }
    )

