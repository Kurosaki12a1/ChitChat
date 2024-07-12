package domain.usecase.chat

import data.model.dto.ChatRoomDto
import domain.model.PrivateChatRequest
import domain.repository.remote.SessionChatRepository

class StartPrivateChatUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(request: PrivateChatRequest): ChatRoomDto? {
        return repository.startPrivateChat(request)
    }
}