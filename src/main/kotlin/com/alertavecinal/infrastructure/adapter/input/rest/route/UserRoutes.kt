package com.alertavecinal.infrastructure.adapter.input.rest.route

import com.alertavecinal.application.port.input.GetUserProfileUseCase
import com.alertavecinal.application.port.input.UpdateUserProfileUseCase
import com.alertavecinal.domain.exception.UserNotFoundException
import com.alertavecinal.infrastructure.adapter.input.rest.dto.ErrorResponse
import com.alertavecinal.infrastructure.adapter.input.rest.dto.UpdateUserProfileRequest
import com.alertavecinal.infrastructure.adapter.input.rest.mapper.UserMapper
import com.alertavecinal.infrastructure.config.getEmail
import com.alertavecinal.infrastructure.config.getUserId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.UUID

/**
 * REST API routes for user operations.
 * Follows RESTful conventions.
 */
fun Route.userRoutes() {
    val getUserProfileUseCase: GetUserProfileUseCase by inject()
    val updateUserProfileUseCase: UpdateUserProfileUseCase by inject()

    route("/users") {
        // Protected endpoints - require authentication
        authenticate("supabase-jwt") {
            // GET /api/v1/users/me - Get current user's profile
            get("/me") {
                try {
                    val principal = call.principal<JWTPrincipal>()
                        ?: return@get call.respond(
                            HttpStatusCode.Unauthorized,
                            ErrorResponse("Unauthorized", "Authentication required")
                        )

                    val userId = UUID.fromString(principal.getUserId())
                    val user = getUserProfileUseCase.execute(userId)
                    val response = UserMapper.toResponse(user)

                    call.respond(HttpStatusCode.OK, response)
                } catch (e: UserNotFoundException) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse("User not found", e.message, call.request.uri)
                    )
                } catch (e: Exception) {
                    call.application.environment.log.error("Error getting user profile", e)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ErrorResponse("Internal error", "An unexpected error occurred")
                    )
                }
            }

            // PATCH /api/v1/users/me - Update current user's profile
            patch("/me") {
                try {
                    val principal = call.principal<JWTPrincipal>()
                        ?: return@patch call.respond(
                            HttpStatusCode.Unauthorized,
                            ErrorResponse("Unauthorized", "Authentication required")
                        )

                    val userId = UUID.fromString(principal.getUserId())
                    val request = call.receive<UpdateUserProfileRequest>()
                    val newProfile = UserMapper.toUserProfile(request)

                    val updatedUser = updateUserProfileUseCase.execute(userId, newProfile)
                    val response = UserMapper.toResponse(updatedUser)

                    call.respond(HttpStatusCode.OK, response)
                } catch (e: UserNotFoundException) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse("User not found", e.message, call.request.uri)
                    )
                } catch (e: IllegalArgumentException) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse("Validation error", e.message ?: "Invalid request data")
                    )
                } catch (e: Exception) {
                    call.application.environment.log.error("Error updating user profile", e)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ErrorResponse("Internal error", "An unexpected error occurred")
                    )
                }
            }

            // GET /api/v1/users/{id} - Get public profile of another user
            get("/{id}") {
                try {
                    val userIdParam = call.parameters["id"]
                        ?: return@get call.respond(
                            HttpStatusCode.BadRequest,
                            ErrorResponse("Bad request", "User ID is required")
                        )

                    val userId = UUID.fromString(userIdParam)
                    val user = getUserProfileUseCase.execute(userId)
                    val response = UserMapper.toPublicProfileResponse(user)

                    call.respond(HttpStatusCode.OK, response)
                } catch (e: IllegalArgumentException) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse("Bad request", "Invalid user ID format")
                    )
                } catch (e: UserNotFoundException) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse("User not found", e.message, call.request.uri)
                    )
                } catch (e: Exception) {
                    call.application.environment.log.error("Error getting user profile", e)
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ErrorResponse("Internal error", "An unexpected error occurred")
                    )
                }
            }
        }
    }
}
