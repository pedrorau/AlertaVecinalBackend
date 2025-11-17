package com.alertavecinal.infrastructure.config

import com.alertavecinal.infrastructure.adapter.input.rest.route.userRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Configures all API routes.
 * Organizes routes by version and resource.
 */
fun Application.configureRouting() {
    routing {
        // Health check endpoint (no authentication required)
        get("/health") {
            call.respond(
                mapOf(
                    "status" to "UP",
                    "service" to "AlertaVecinalBackend",
                    "version" to "0.0.1"
                )
            )
        }

        // API v1 routes
        route("/api/v1") {
            userRoutes()
            // Future routes will be added here:
            // alertRoutes()
            // neighborhoodRoutes()
        }
    }
}
