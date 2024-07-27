package domain.usecase.chat

import data.model.dto.ChatRoomDto
import domain.repository.remote.SessionChatRepository

class JoinPublicChatRoomUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(roomId: String, userId: String): ChatRoomDto? {
        return repository.joinPublicChatRoom(roomId, userId)
    }
}
