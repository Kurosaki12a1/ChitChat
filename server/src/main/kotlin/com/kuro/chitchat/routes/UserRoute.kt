package com.kuro.chitchat.routes

import com.kuro.chitchat.data.model.dto.ApiResponse
import com.kuro.chitchat.data.model.toDTO
import com.kuro.chitchat.domain.model.Endpoint
import com.kuro.chitchat.domain.model.UserSession
import com.kuro.chitchat.domain.model.UserUpdate
import com.kuro.chitchat.domain.repository.UserDataSource
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions
import io.ktor.util.pipeline.PipelineContext
import utils.AUTH_SESSION

fun Route.userRoute(
    app: Application,
    userDataSource: UserDataSource
) {

    authenticate(AUTH_SESSION) {
        /**
         * This endpoint is for  current user with own session only. Not allow for other user not
         * signed in your device. Use for update last active
         *
         * To get another user, see SearchRoute
         */
        get(Endpoint.SignIn.path) {
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                app.log.error("Invalid Session when get: ${Endpoint.SignIn.path}")
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            userDto = userDataSource.updateUserLastActive(userId = userSession.id)
                                ?.toDTO()
                        ),
                        status = HttpStatusCode.OK
                    )
                } catch (e: Exception) {
                    app.log.info("Sign In error: ${e.message}")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
        get(Endpoint.GetUserInfo.path) {
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                app.log.error("Invalid Session when get: ${Endpoint.GetUserInfo.path}")
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            userDto = userDataSource.getUserInfo(userId = userSession.id)
                                ?.toDTO()
                        ),
                        status = HttpStatusCode.OK
                    )
                } catch (e: Exception) {
                    app.log.info("GETTING USER INFO ERROR: ${e.message}")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
        put(Endpoint.UpdateUserInfo.path) {
            val userSession = call.principal<UserSession>()
            val userUpdate = call.receive<UserUpdate>()
            if (userSession == null) {
                app.log.error("Invalid Session when put: ${Endpoint.UpdateUserInfo.path}")
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    updateUserInfo(
                        app = app,
                        userId = userSession.id,
                        userUpdate = userUpdate,
                        userDataSource = userDataSource
                    )
                } catch (e: Exception) {
                    app.log.info("UPDATE USER INFO ERROR: $e")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
        delete(Endpoint.DeleteUser.path) {
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                app.log.error("Invalid Session when delete: ${Endpoint.DeleteUser.path}")
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    call.sessions.clear<UserSession>()
                    deleteUserFromDb(
                        app = app,
                        userId = userSession.id,
                        userDataSource = userDataSource
                    )
                } catch (e: Exception) {
                    app.log.info("DELETING USER ERROR: ${e.message}")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
        get(Endpoint.SignOut.path) {
            call.sessions.clear<UserSession>()
            call.respond(
                message = ApiResponse(success = true),
                status = HttpStatusCode.OK
            )
        }

    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.deleteUserFromDb(
    app: Application,
    userId: String,
    userDataSource: UserDataSource
) {
    val result = userDataSource.deleteUser(userId = userId)
    if (result) {
        app.log.info("USER SUCCESSFULLY DELETED")
        call.respond(
            message = ApiResponse(success = true),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR DELETING THE USER")
        call.respond(
            message = ApiResponse(success = false),
            status = HttpStatusCode.BadRequest
        )
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.updateUserInfo(
    app: Application,
    userId: String,
    userUpdate: UserUpdate,
    userDataSource: UserDataSource
) {
    val response = userDataSource.updateUserName(
        userId = userId,
        firstName = userUpdate.firstName,
        lastName = userUpdate.lastName
    )
    if (response) {
        app.log.info("USER SUCCESSFULLY UPDATED")
        call.respond(
            message = data.model.dto.ApiResponse(
                success = true,
                message = "Successfully Updated!"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR UPDATING THE USER")
        call.respond(
            message = data.model.dto.ApiResponse(success = false),
            status = HttpStatusCode.BadRequest
        )
    }
}