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
import data.repository.local.ChatRoomRepositoryImpl
import data.repository.local.MessageRepositoryImpl
import data.repository.local.UserRepositoryImpl
import data.repository.remote.ApiRepositoryImpl
import data.repository.remote.AuthRepositoryImpl
import domain.repository.DataStoreOperations
import domain.repository.local.ChatRoomRepository
import domain.repository.local.MessageRepository
import domain.repository.local.UserRepository
import domain.repository.remote.ApiRepository
import domain.repository.remote.AuthRepository
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

    single<ApiRepository> { ApiRepositoryImpl(get()) }
    single<DataStoreOperations> { DataStoreOperationsImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    single<UserDao> { createUserDao(get()) }
    single<ChatRoomDao> { createChatRoomDao(get()) }
    single<MessageDao> { createMessageDao(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<ChatRoomRepository> { ChatRoomRepositoryImpl(get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
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
