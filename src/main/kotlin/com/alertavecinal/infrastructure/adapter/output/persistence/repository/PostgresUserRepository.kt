package com.alertavecinal.infrastructure.adapter.output.persistence.repository

import com.alertavecinal.application.port.output.UserRepository
import com.alertavecinal.domain.model.User
import com.alertavecinal.infrastructure.adapter.output.persistence.entity.Users
import com.alertavecinal.infrastructure.adapter.output.persistence.mapper.UserEntityMapper
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

/**
 * PostgreSQL implementation of UserRepository using Exposed.
 * Connects to Supabase PostgreSQL database.
 */
class PostgresUserRepository : UserRepository {

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    override suspend fun findById(userId: UUID): User? = dbQuery {
        Users.select { Users.id eq userId }
            .singleOrNull()
            ?.let { UserEntityMapper.toDomain(it) }
    }

    override suspend fun findByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }
            .singleOrNull()
            ?.let { UserEntityMapper.toDomain(it) }
    }

    override suspend fun save(user: User): User = dbQuery {
        val exists = Users.select { Users.id eq user.id }.singleOrNull() != null

        if (exists) {
            // Update existing user
            Users.update({ Users.id eq user.id }) {
                it[email] = user.email
                it[displayName] = user.profile.displayName
                it[photoUrl] = user.profile.photoUrl
                it[phoneNumber] = user.profile.phoneNumber
                it[address] = user.profile.address
                it[bio] = user.profile.bio
                it[updatedAt] = user.updatedAt
            }
        } else {
            // Insert new user
            Users.insert {
                it[id] = user.id
                it[email] = user.email
                it[displayName] = user.profile.displayName
                it[photoUrl] = user.profile.photoUrl
                it[phoneNumber] = user.profile.phoneNumber
                it[address] = user.profile.address
                it[bio] = user.profile.bio
                it[createdAt] = user.createdAt
                it[updatedAt] = user.updatedAt
            }
        }

        user
    }

    override suspend fun deleteById(userId: UUID): Boolean = dbQuery {
        Users.deleteWhere { id eq userId } > 0
    }

    override suspend fun existsById(userId: UUID): Boolean = dbQuery {
        Users.select { Users.id eq userId }.singleOrNull() != null
    }
}
