/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.appconfig.bean

import com.github.francescojo.core.domain.user.repository.UserReadonlyRepository
import com.github.francescojo.core.domain.user.repository.writable.UserRepository
import com.github.francescojo.core.domain.user.usecase.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * @since 2021-08-10
 */
@Configuration
class UserBeans {
    @Bean
    fun createUserUseCase(
        userRepository: UserRepository,
        passwordEncoderService: PasswordEncoderService,
    ) = CreateUserUseCase.newInstance(
        userRepository,
        passwordEncoderService
    )

    @Bean
    fun getUserUseCase(
        userReadonlyRepository: UserReadonlyRepository
    ) = FindUserUseCase.newInstance(
        userReadonlyRepository
    )

    @Bean
    fun editUserUseCase(
        userRepository: UserRepository,
    ) = EditUserUseCase.newInstance(
        userRepository
    )

    @Bean
    fun deleteUserUseCase(
        userRepository: UserRepository,
    ) = DeleteUserUseCase.newInstance(
        userRepository
    )
}
