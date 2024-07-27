package data.repository.remote

import data.model.dto.UserDto
import domain.repository.remote.SearchRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import utils.PARAMETER_SEARCH
import utils.SEARCH_USER

class SearchRepositoryImpl(private val httpClient: HttpClient) : SearchRepository {
    override suspend fun searchByName(name: String): List<UserDto>? {
        return try {
            val response = httpClient.get {
                url {
                    path(SEARCH_USER)
                    contentType(ContentType.Application.Json)
                    parameter(PARAMETER_SEARCH, name)
                }
            }
            response.body<List<UserDto>>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}