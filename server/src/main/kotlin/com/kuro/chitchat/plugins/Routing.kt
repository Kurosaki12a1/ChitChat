package com.kuro.chitchat.plugins

import com.kuro.chitchat.domain.repository.UserDataSource
import com.kuro.chitchat.routes.authorizedRoute
import com.kuro.chitchat.routes.deleteUserRoute
import com.kuro.chitchat.routes.rootRoute
import com.kuro.chitchat.routes.searchRoute
import com.kuro.chitchat.routes.signOutRoute
import com.kuro.chitchat.routes.tokenVerificationRoute
import com.kuro.chitchat.routes.unauthorizedRoute
import com.kuro.chitchat.routes.updateUserRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {
    routing {
        val userDataSource: UserDataSource by inject(UserDataSource::class.java)
        rootRoute()
        tokenVerificationRoute(application, userDataSource)
        getUserInfoRoute(application, userDataSource)
        updateUserRoute(application, userDataSource)
        deleteUserRoute(application, userDataSource)
        searchRoute(application, userDataSource)
        signOutRoute()
        authorizedRoute()
        unauthorizedRoute()
    }
}