package domain.usecase.search

import data.model.dto.UserDto
import domain.repository.remote.SearchRepository

class SearchUserUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(name: String): Pair<List<UserDto>?, Exception?> {
        if (name.length < 2) {
            return Pair(emptyList(), Exception("You must input 2 letters above!"))
        }
        return Pair(repository.searchByName(name), null)
    }
}