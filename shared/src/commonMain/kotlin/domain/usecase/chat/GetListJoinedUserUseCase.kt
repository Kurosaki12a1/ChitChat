package domain.usecase.chat

import data.model.dto.UserDto
import domain.repository.remote.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetListJoinedUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(participants: List<String>): List<UserDto> =
        withContext(Dispatchers.IO) {
            val listJoined = mutableListOf<UserDto>()
            if (participants.isEmpty()) return@withContext emptyList()
            participants.forEach {
                val data = authRepository.getUserInfo(it)
                data.user?.let { u -> listJoined.add(u) }
            }
            return@withContext listJoined
        }
}