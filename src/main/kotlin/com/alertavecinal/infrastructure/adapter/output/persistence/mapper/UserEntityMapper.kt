package com.alertavecinal.infrastructure.adapter.output.persistence.mapper

import com.alertavecinal.domain.model.User
import com.alertavecinal.domain.model.UserProfile
import com.alertavecinal.infrastructure.adapter.output.persistence.entity.Users
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

/**
 * Mapper for converting between database entities and domain models.
 */
object UserEntityMapper {

    /**
     * Maps database ResultRow to domain User
     */
    fun toDomain(row: ResultRow): User {
        return User(
            id = row[Users.id].value,
            email = row[Users.email],
            profile = UserProfile(
                displayName = row[Users.displayName],
                photoUrl = row[Users.photoUrl],
                phoneNumber = row[Users.phoneNumber],
                address = row[Users.address],
                bio = row[Users.bio]
            ),
            createdAt = row[Users.createdAt],
            updatedAt = row[Users.updatedAt]
        )
    }

    /**
     * Creates a map for database insertion from domain User
     */
    fun fromDomain(user: User): Map<org.jetbrains.exposed.sql.Column<*>, Any?> {
        return mapOf(
            Users.id to user.id,
            Users.email to user.email,
            Users.displayName to user.profile.displayName,
            Users.photoUrl to user.profile.photoUrl,
            Users.phoneNumber to user.profile.phoneNumber,
            Users.address to user.profile.address,
            Users.bio to user.profile.bio,
            Users.createdAt to user.createdAt,
            Users.updatedAt to user.updatedAt
        )
    }
}
