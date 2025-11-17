package com.alertavecinal.infrastructure.config

import com.alertavecinal.application.port.input.GetUserProfileUseCase
import com.alertavecinal.application.port.input.UpdateUserProfileUseCase
import com.alertavecinal.application.port.output.UserRepository
import com.alertavecinal.application.service.UserService
import com.alertavecinal.infrastructure.adapter.output.persistence.repository.PostgresUserRepository
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

/**
 * Configures Koin dependency injection.
 * Defines all application modules and their dependencies.
 */
fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            repositoryModule,
            serviceModule
        )
    }
}

/**
 * Repository layer module - infrastructure adapters
 */
val repositoryModule = module {
    single<UserRepository> { PostgresUserRepository() }
}

/**
 * Service layer module - application use cases
 */
val serviceModule = module {
    single<UserService> { UserService(get()) }
    single<GetUserProfileUseCase> { get<UserService>() }
    single<UpdateUserProfileUseCase> { get<UserService>() }
}
