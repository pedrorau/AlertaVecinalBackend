package com.alertavecinal.infrastructure.adapter.output.persistence.entity

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

/**
 * Exposed table definition for users.
 * Maps to the users table in Supabase/PostgreSQL.
 */
object Users : UUIDTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val displayName = varchar("display_name", 100).nullable()
    val photoUrl = varchar("photo_url", 500).nullable()
    val phoneNumber = varchar("phone_number", 20).nullable()
    val address = varchar("address", 255).nullable()
    val bio = text("bio").nullable()
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
}
