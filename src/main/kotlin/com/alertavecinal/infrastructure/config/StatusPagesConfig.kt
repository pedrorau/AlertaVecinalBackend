package com.alertavecinal.infrastructure.config

import com.alertavecinal.domain.exception.DomainException
import com.alertavecinal.domain.exception.UnauthorizedException
import com.alertavecinal.domain.exception.UserNotFoundException
import com.alertavecinal.infrastructure.adapter.input.rest.dto.ErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

/**
 * Configures global exception handling.
 * Maps domain exceptions to appropriate HTTP responses.
 */
fun Application.configureStatusPages() {
    install(StatusPages) {
        // User not found
        exception<UserNotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(
                    error = "Not Found",
                    message = cause.message,
                    path = call.request.local.uri
                )
            )
        }

        // Unauthorized
        exception<UnauthorizedException> { call, cause ->
            call.respond(
                HttpStatusCode.Forbidden,
                ErrorResponse(
                    error = "Forbidden",
                    message = cause.message,
                    path = call.request.local.uri
                )
            )
        }

        // Generic domain exceptions (business rule violations)
        exception<DomainException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "Bad Request",
                    message = cause.message,
                    path = call.request.local.uri
                )
            )
        }

        // Validation errors
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "Validation Error",
                    message = cause.message ?: "Invalid request data",
                    path = call.request.local.uri
                )
            )
        }

        // Generic server errors
        exception<Throwable> { call, cause ->
            call.application.environment.log.error("Unhandled exception", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "Internal Server Error",
                    message = "An unexpected error occurred",
                    path = call.request.local.uri
                )
            )
        }
    }
}
