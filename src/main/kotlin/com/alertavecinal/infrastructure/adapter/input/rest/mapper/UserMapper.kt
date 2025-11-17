package com.alertavecinal.infrastructure.adapter.input.rest.mapper

import com.alertavecinal.domain.model.User
import com.alertavecinal.domain.model.UserProfile
import com.alertavecinal.infrastructure.adapter.input.rest.dto.UpdateUserProfileRequest
import com.alertavecinal.infrastructure.adapter.input.rest.dto.UserPublicProfileResponse
import com.alertavecinal.infrastructure.adapter.input.rest.dto.UserResponse

/**
 * Mapper for converting between domain models and DTOs.
 * Follows the adapter pattern in hexagonal architecture.
 */
object UserMapper {

    /**
     * Maps domain User to UserResponse DTO
     */
    fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id.toString(),
            email = user.email,
            displayName = user.profile.displayName,
            photoUrl = user.profile.photoUrl,
            phoneNumber = user.profile.phoneNumber,
            address = user.profile.address,
            bio = user.profile.bio,
            createdAt = user.createdAt.toString(),
            updatedAt = user.updatedAt.toString()
        )
    }

    /**
     * Maps domain User to public profile response (limited info)
     */
    fun toPublicProfileResponse(user: User): UserPublicProfileResponse {
        return UserPublicProfileResponse(
            id = user.id.toString(),
            displayName = user.profile.displayName,
            photoUrl = user.profile.photoUrl,
            bio = user.profile.bio
        )
    }

    /**
     * Maps UpdateUserProfileRequest to UserProfile domain model
     */
    fun toUserProfile(request: UpdateUserProfileRequest): UserProfile {
        return UserProfile(
            displayName = request.displayName,
            photoUrl = request.photoUrl,
            phoneNumber = request.phoneNumber,
            address = request.address,
            bio = request.bio
        )
    }
}
