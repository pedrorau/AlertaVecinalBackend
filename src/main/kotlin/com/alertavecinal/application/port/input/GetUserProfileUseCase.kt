package com.alertavecinal.application.port.input

import com.alertavecinal.domain.model.User
import java.util.UUID

/**
 * Use case for retrieving a user's profile.
 * Input port following Hexagonal Architecture.
 */
interface GetUserProfileUseCase {
    /**
     * Gets the profile of the specified user
     * @param userId The UUID of the user
     * @return The user if found
     * @throws UserNotFoundException if user doesn't exist
     */
    suspend fun execute(userId: UUID): User
}
