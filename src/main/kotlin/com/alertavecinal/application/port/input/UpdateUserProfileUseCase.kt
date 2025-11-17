package com.alertavecinal.application.port.input

import com.alertavecinal.domain.model.User
import com.alertavecinal.domain.model.UserProfile
import java.util.UUID

/**
 * Use case for updating a user's profile.
 * Input port following Hexagonal Architecture.
 */
interface UpdateUserProfileUseCase {
    /**
     * Updates the profile of the specified user
     * @param userId The UUID of the user performing the update
     * @param newProfile The new profile data
     * @return The updated user
     * @throws UserNotFoundException if user doesn't exist
     * @throws UnauthorizedException if user is not authorized
     */
    suspend fun execute(userId: UUID, newProfile: UserProfile): User
}
