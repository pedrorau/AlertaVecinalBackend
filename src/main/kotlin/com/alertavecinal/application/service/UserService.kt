package com.alertavecinal.application.service

import com.alertavecinal.application.port.input.GetUserProfileUseCase
import com.alertavecinal.application.port.input.UpdateUserProfileUseCase
import com.alertavecinal.application.port.output.UserRepository
import com.alertavecinal.domain.exception.UserNotFoundException
import com.alertavecinal.domain.model.User
import com.alertavecinal.domain.model.UserProfile
import kotlinx.datetime.Clock
import java.util.UUID

/**
 * Service implementing user-related use cases.
 * Orchestrates domain logic and repository operations.
 */
class UserService(
    private val userRepository: UserRepository
) : GetUserProfileUseCase, UpdateUserProfileUseCase {

    override suspend fun execute(userId: UUID): User {
        return userRepository.findById(userId)
            ?: throw UserNotFoundException(userId.toString())
    }

    override suspend fun execute(userId: UUID, newProfile: UserProfile): User {
        val existingUser = userRepository.findById(userId)
            ?: throw UserNotFoundException(userId.toString())

        val updatedUser = existingUser.updateProfile(
            newProfile = newProfile,
            updatedAt = Clock.System.now()
        )

        return userRepository.save(updatedUser)
    }
}
