package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import data.network.ChitChatApi
import data.repository.AuthRepositoryImpl
import data.repository.DataStoreOperationsImpl
import domain.repository.AuthRepository
import domain.repository.DataStoreOperations
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
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
    single { createHttpClient(get(), get()) }

    single { ChitChatApi(get()) }
    single<DataStoreOperations> { DataStoreOperationsImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; prettyPrint = true }

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json) =
    HttpClient(httpClientEngine) {
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
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

fun createDataStore(
    producePath: () -> String,
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = null,
    migrations = emptyList(),
    produceFile = { producePath().toPath() },
)

