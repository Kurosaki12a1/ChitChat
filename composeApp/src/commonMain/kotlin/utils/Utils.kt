package utils

import domain.models.MessageModel

object Utils {
    fun isContinuousSenderId(list: List<MessageModel>, currentIndex: Int): Boolean {
        if (currentIndex <= 0 || currentIndex >= list.size) return false

        val currentSenderId = list[currentIndex].senderId
        val prevSenderId = list.getOrNull(currentIndex - 1)?.senderId
        val nextSenderId = list.getOrNull(currentIndex + 1)?.senderId

        return currentSenderId == prevSenderId || currentSenderId == nextSenderId
    }
}