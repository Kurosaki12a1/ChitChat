package com.kuro.chitchat.routes

import com.kuro.chitchat.data.mapper.toDTO
import com.kuro.chitchat.database.server.domain.repository.UserDataSource
import com.kuro.chitchat.domain.model.Endpoint
import com.kuro.chitchat.domain.model.UserSession
import com.kuro.chitchat.domain.model.UserUpdate
import data.model.dto.ApiResponse
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

/**
 * Defines the user-related routes for the Ktor application.
 *
 * @param app The Ktor Application instance.
 * @param userDataSource The data source for user-related operations.
 */
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
            // Retrieves the user session from the call
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                // Logs an error message if the session is invalid
                app.log.error("Invalid Session when get: ${Endpoint.SignIn.path}")
                // Redirects the user to the unauthorized endpoint if the session is invalid
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    // Responds with the user data if the session is valid
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            user = userDataSource.updateUserLastActive(userId = userSession.id)
                                ?.toDTO()
                        ),
                        status = HttpStatusCode.OK
                    )
                } catch (e: Exception) {
                    // Logs an error message if there is an exception
                    app.log.info("Sign In error: ${e.message}")
                    // Redirects the user to the unauthorized endpoint if there is an exception
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
        /**
         * Endpoint for getting user information.
         * Only accessible by the user with a valid session.
         */
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
                            user = userDataSource.getUserInfo(userId = userSession.id)
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

        /**
         * Endpoint for updating user information.
         * Only accessible by the user with a valid session.
         */
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

        /**
         * Endpoint for deleting the user.
         * Only accessible by the user with a valid session.
         */
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

        /**
         * Endpoint for signing out the user.
         * Clears the user's session.
         */
        get(Endpoint.SignOut.path) {
            call.sessions.clear<UserSession>()
            call.respond(
                message = ApiResponse(success = true),
                status = HttpStatusCode.OK
            )
        }

    }
}

/**
 * Deletes a user from the database and responds with the result.
 *
 * @param app The Ktor Application instance.
 * @param userId The ID of the user to be deleted.
 * @param userDataSource The data source for user-related operations.
 */
private suspend fun PipelineContext<Unit, ApplicationCall>.deleteUserFromDb(
    app: Application,
    userId: String,
    userDataSource: UserDataSource
) {
    val result = userDataSource.deleteUser(userId = userId)
    if (result) {
        // Logs a success message if the user is successfully deleted
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

/**
 * Updates user information in the database and responds with the result.
 *
 * @param app The Ktor Application instance.
 * @param userId The ID of the user to be updated.
 * @param userUpdate The UserUpdate object containing the updated information.
 * @param userDataSource The data source for user-related operations.
 */
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
            message = ApiResponse(
                success = true,
                message = "Successfully Updated!"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR UPDATING THE USER")
        call.respond(
            message = ApiResponse(success = false),
            status = HttpStatusCode.BadRequest
        )
    }
}