package com.kuro.chitchat.routes

import com.kuro.chitchat.data.mapper.toDTO
import com.kuro.chitchat.database.server.domain.repository.UserDataSource
import com.kuro.chitchat.domain.model.Endpoint
import data.model.dto.UserDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import utils.AUTH_SESSION
import utils.PARAMETER_SEARCH

fun Route.searchRoute(
    app: Application,
    userDataSource: UserDataSource
) {
    authenticate(AUTH_SESSION) {
        get(Endpoint.SearchUser.path) {
            val name = call.request.queryParameters[PARAMETER_SEARCH]
            name?.let {
                val result = userDataSource.searchUsers(it)
                if (result.isNotEmpty()) {
                    app.log.info("Search user result: $result")
                    call.respond(
                        message = result.map { item -> item.toDTO() },
                        status = HttpStatusCode.OK
                    )
                } else {
                    app.log.info("Search user result: Empty")
                    call.respond(
                        message = emptyList<UserDto>(),
                        status = HttpStatusCode.OK
                    )
                }
            }
        }
    }
}