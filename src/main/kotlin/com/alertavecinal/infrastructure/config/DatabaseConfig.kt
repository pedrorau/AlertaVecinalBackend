package com.alertavecinal.infrastructure.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.alertavecinal.infrastructure.adapter.output.persistence.entity.Users

/**
 * Configures database connection to Supabase PostgreSQL.
 * Uses HikariCP for connection pooling.
 */
fun Application.configureDatabases(): Database {
    val dbUrl = environment.config.property("database.url").getString()
    val dbUser = environment.config.property("database.user").getString()
    val dbPassword = environment.config.property("database.password").getString()
    val dbDriver = environment.config.propertyOrNull("database.driver")?.getString()
        ?: "org.postgresql.Driver"
    val poolSize = environment.config.propertyOrNull("database.poolSize")?.getString()?.toInt()
        ?: 10

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = dbUrl
        username = dbUser
        password = dbPassword
        driverClassName = dbDriver
        maximumPoolSize = poolSize
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    val dataSource = HikariDataSource(hikariConfig)
    val database = Database.connect(dataSource)

    // Create tables if they don't exist (for development)
    // In production, use database migrations (Flyway/Liquibase)
    transaction(database) {
        SchemaUtils.createMissingTablesAndColumns(Users)
    }

    return database
}
