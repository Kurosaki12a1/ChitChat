package domain.repository.remote

import data.model.dto.UserDto

interface SearchRepository {
    suspend fun searchByName(name : String) : List<UserDto>?
}