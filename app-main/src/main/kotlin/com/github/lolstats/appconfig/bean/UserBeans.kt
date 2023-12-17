/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.appconfig.bean

import com.github.lolstats.core.domain.user.repository.UserReadonlyRepository
import com.github.lolstats.core.domain.user.repository.writable.UserRepository
import com.github.lolstats.core.domain.user.usecase.CreateUserUseCase
import com.github.lolstats.core.domain.user.usecase.DeleteUserUseCase
import com.github.lolstats.core.domain.user.usecase.EditUserUseCase
import com.github.lolstats.core.domain.user.usecase.FindUserUseCase
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
    ) = CreateUserUseCase.newInstance(
        userRepository
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
