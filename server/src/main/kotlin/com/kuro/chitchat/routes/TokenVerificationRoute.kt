package com.kuro.chitchat.routes


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.kuro.chitchat.domain.model.ApiRequest
import com.kuro.chitchat.domain.model.ApiResponse
import com.kuro.chitchat.domain.model.Endpoint
import com.kuro.chitchat.domain.model.User
import com.kuro.chitchat.domain.model.UserSession
import com.kuro.chitchat.domain.repository.UserDataSource
import com.kuro.chitchat.util.Constants.ISSUER
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.util.pipeline.PipelineContext
import utils.CLIENT_ID
import java.util.Collections


fun Route.tokenVerificationRoute(
    app: Application,
    userDataSource: UserDataSource
) {
    post(Endpoint.TokenVerification.path) {
        val request = call.receive<ApiRequest>()
        if (request.tokenId.isNotEmpty()) {
            val result = verifyGoogleTokenId(tokenId = request.tokenId)
            if (result != null) {
                saveUserToDatabase(
                    app = app,
                    result = result,
                    userDataSource = userDataSource
                )
            } else {
                app.log.info("TOKEN VERIFICATION FAILED")
                call.response.status(HttpStatusCode.BadRequest)
                call.respond(ApiResponse(success = false, message = "Bad Request."))
            }
        } else {
            app.log.info("EMPTY TOKEN ID")
            call.response.status(HttpStatusCode.Unauthorized)
            call.respond(ApiResponse(success = false, message = "Not Authorized."))
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.saveUserToDatabase(
    app: Application,
    result: GoogleIdToken,
    userDataSource: UserDataSource
) {
    val sub = result.payload["sub"].toString()
    val name = result.payload["name"].toString()
    val emailAddress = result.payload["email"].toString()
    val profilePhoto = result.payload["picture"].toString()
    val user = User(
        id = sub,
        name = name,
        emailAddress = emailAddress,
        profilePhoto = profilePhoto
    )

    val response = userDataSource.saveUserInfo(user = user)
    if (response) {
        app.log.info("USER SUCCESSFULLY SAVED/RETRIEVED")
        call.sessions.set(UserSession(id = sub, name = name))
        call.response.status(HttpStatusCode.OK)
        call.respond(ApiResponse(success = true, user = user, message = "User login successfully!"))
    } else {
        app.log.info("ERROR SAVING THE USER")
        call.response.status(HttpStatusCode.Conflict)
        call.respond(ApiResponse(success = false, message = HttpStatusCode.Conflict.description))
    }
}

fun verifyGoogleTokenId(tokenId: String): GoogleIdToken? {
    return try {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(Collections.singleton(CLIENT_ID))
            .setIssuer(ISSUER)
            .build()
        verifier.verify(tokenId)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}