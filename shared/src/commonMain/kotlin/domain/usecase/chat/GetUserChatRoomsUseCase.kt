package domain.usecase.chat

import data.model.dto.ChatRoomDto
import domain.repository.remote.SessionChatRepository

class GetUserChatRoomsUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(userId: String): List<ChatRoomDto> {
        return repository.getUserChatRooms(userId)
    }
}