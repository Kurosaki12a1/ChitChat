package domain.usecase.chat

import data.model.dto.ChatRoomDto
import domain.models.ChatRoomModel
import domain.repository.remote.SessionChatRepository

class CreatePublicChatRoomUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(room: ChatRoomModel, creatorId: String): ChatRoomDto? {
        return repository.createPublicChatRoom(room, creatorId)
    }
}
