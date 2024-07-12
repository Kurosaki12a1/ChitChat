package domain.usecase.chat

import data.model.dto.HistoryChatRoomDto
import domain.repository.remote.SessionChatRepository

class GetChatHistoryUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(roomId: String): HistoryChatRoomDto {
        return repository.getChatHistory(roomId)
    }
}