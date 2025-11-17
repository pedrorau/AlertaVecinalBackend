package com.alertavecinal.application.port.output

import com.alertavecinal.domain.model.User
import java.util.UUID

/**
 * Repository port for user persistence.
 * Output port following Hexagonal Architecture.
 * Implementations should be provided by the infrastructure layer.
 */
interface UserRepository {
    /**
     * Finds a user by their ID
     * @param userId The user's UUID
     * @return The user if found, null otherwise
     */
    suspend fun findById(userId: UUID): User?

    /**
     * Finds a user by their email
     * @param email The user's email
     * @return The user if found, null otherwise
     */
    suspend fun findByEmail(email: String): User?

    /**
     * Saves a user (create or update)
     * @param user The user to save
     * @return The saved user
     */
    suspend fun save(user: User): User

    /**
     * Deletes a user by their ID
     * @param userId The user's UUID
     * @return true if deleted, false if not found
     */
    suspend fun deleteById(userId: UUID): Boolean

    /**
     * Checks if a user exists by ID
     * @param userId The user's UUID
     * @return true if exists, false otherwise
     */
    suspend fun existsById(userId: UUID): Boolean
}
