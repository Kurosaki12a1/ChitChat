package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import data.data_source.local.AppDatabase
import data.data_source.local.cookie.CookieStorageManager
import data.data_source.local.dao.ChatRoomDao
import data.data_source.local.dao.MessageDao
import data.data_source.local.dao.UserDao
import data.repository.DataStoreOperationsImpl
import data.repository.local.LocalChatRoomDataSourceImpl
import data.repository.local.LocalMessageDataSourceImpl
import data.repository.local.LocalUserDataSourceImpl
import data.repository.remote.AuthRepositoryImpl
import data.repository.remote.ChatRoomRemoteRepositoryImpl
import data.repository.remote.SessionChatRepositoryImpl
import data.repository.remote.SocketRepositoryImpl
import domain.repository.DataStoreOperations
import domain.repository.local.LocalChatRoomDataSource
import domain.repository.local.LocalMessageDataSource
import domain.repository.local.LocalUserDataSource
import domain.repository.remote.AuthRepository
import domain.repository.remote.ChatRoomRemoteRepository
import domain.repository.remote.SessionChatRepository
import domain.repository.remote.SocketRepository
import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.ConnectToWebSocketUseCase
import domain.usecase.chat.CreatePublicChatRoomUseCase
import domain.usecase.chat.GetChatHistoryUseCase
import domain.usecase.chat.GetUserChatRoomsUseCase
import domain.usecase.chat.JoinPublicChatRoomUseCase
import domain.usecase.chat.ReceiveMessagesUseCase
import domain.usecase.chat.SendMessageUseCase
import domain.usecase.chat.SessionChatUseCase
import domain.usecase.chat.SocketUseCase
import domain.usecase.chat.StartPrivateChatUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import platformModule
import utils.CONNECTION_TIME_OUT
import utils.DOMAIN
import utils.HOST
import utils.REQUEST_TIME_OUT
import utils.SERVER_PORT
import utils.SOCKET_TIME_OUT

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(), platformModule())
    }

// called by iOS etc
fun initKoin() = initKoin {}

fun commonModule() = module {
    single { createJson() }
    single { createHttpClient(get(), get(), get()) }

    single<DataStoreOperations> { DataStoreOperationsImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ChatRoomRemoteRepository> { ChatRoomRemoteRepositoryImpl(get()) }
    single<SocketRepository> { SocketRepositoryImpl(get()) }
    single<SessionChatRepository> { SessionChatRepositoryImpl(get(), get()) }
    factory<SessionChatUseCase> {
        SessionChatUseCase(
            getUserChatRoomsUseCase = GetUserChatRoomsUseCase((get())),
            createPublicChatRoomUseCase = CreatePublicChatRoomUseCase(get()),
            startPrivateChatUseCase = StartPrivateChatUseCase(get()),
            joinPublicChatRoomUseCase = JoinPublicChatRoomUseCase(get()),
            getChatHistoryUseCase = GetChatHistoryUseCase(get()),
            connectToWebSocketUseCase = ConnectToWebSocketUseCase(get()),
            sendMessageUseCase = SendMessageUseCase(get()),
            receiveMessagesUseCase = ReceiveMessagesUseCase(get()),
        )
    }

    factory<GetUserInfoUseCase> {
        GetUserInfoUseCase(get(), get())
    }

    factory<SocketUseCase> {
        SocketUseCase(
            connectToWebSocketUseCase = ConnectToWebSocketUseCase(get()),
            sendMessageUseCase = SendMessageUseCase(get()),
            receiveMessagesUseCase = ReceiveMessagesUseCase(get()),
        )
    }

    single<UserDao> { createUserDao(get()) }
    single<ChatRoomDao> { createChatRoomDao(get()) }
    single<MessageDao> { createMessageDao(get()) }

    single<LocalUserDataSource> { LocalUserDataSourceImpl(get()) }
    single<LocalChatRoomDataSource> { LocalChatRoomDataSourceImpl(get()) }
    single<LocalMessageDataSource> { LocalMessageDataSourceImpl(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; prettyPrint = true }

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    dataStore: DataStore<Preferences>
): HttpClient {
    return HttpClient(httpClientEngine) {
        install(HttpCookies) {
            storage = CookieStorageManager(dataStore, DOMAIN)
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIME_OUT
            connectTimeoutMillis = CONNECTION_TIME_OUT
            socketTimeoutMillis = SOCKET_TIME_OUT
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.NONE
        }
        install(WebSockets) {
            pingInterval = 15_000
            maxFrameSize = Long.MAX_VALUE
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = HOST
                port = SERVER_PORT
            }
            contentType(ContentType.Application.Json)
        }
    }
}


fun createDataStore(
    producePath: () -> String,
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = null,
    migrations = emptyList(),
    produceFile = { producePath().toPath() },
)

fun createChatRoomDao(appDatabase: AppDatabase): ChatRoomDao = appDatabase.chatRoomDao()
fun createMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()
fun createUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
