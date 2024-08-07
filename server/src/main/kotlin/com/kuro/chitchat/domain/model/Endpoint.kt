package com.kuro.chitchat.domain.model

import utils.AUTHORIZED
import utils.CHAT
import utils.CHAT_ROOM
import utils.DELETE_USER
import utils.GET_CHAT_HISTORY
import utils.GET_USER
import utils.JOIN_ROOM
import utils.SEARCH_USER
import utils.SIGN_IN
import utils.SIGN_OUT
import utils.TOKEN_VERIFICATION
import utils.UNAUTHORIZED
import utils.UPDATE_USER

sealed class Endpoint(val path: String) {
    data object Root : Endpoint(path = "/")
    data object SignIn : Endpoint(path = SIGN_IN)
    data object TokenVerification : Endpoint(path = TOKEN_VERIFICATION)
    data object GetUserInfo : Endpoint(path = GET_USER)
    data object UpdateUserInfo : Endpoint(path = UPDATE_USER)
    data object DeleteUser : Endpoint(path = DELETE_USER)
    data object SearchUser : Endpoint(path = SEARCH_USER)
    data object SignOut : Endpoint(path = SIGN_OUT)
    data object Unauthorized : Endpoint(path = UNAUTHORIZED)
    data object Authorized : Endpoint(path = AUTHORIZED)
}


sealed class ChatEndPoint(val path : String) {
    data object Chat : Endpoint(path = CHAT)
    data object ChatRoom : Endpoint(path = CHAT_ROOM)
    data object GetChatHistory : Endpoint(path = GET_CHAT_HISTORY)
    data object JoinRoom: Endpoint(path = JOIN_ROOM)
}