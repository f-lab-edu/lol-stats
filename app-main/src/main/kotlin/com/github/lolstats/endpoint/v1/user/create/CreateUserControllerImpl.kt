/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.endpoint.v1.user.create

import com.github.lolstats.core.domain.user.usecase.CreateUserUseCase
import com.github.lolstats.endpoint.v1.user.CreateUserController
import com.github.lolstats.endpoint.v1.user.common.UserResponse
import org.springframework.web.bind.annotation.RestController

/**
 * @since 2021-08-10
 */
@RestController
internal class CreateUserControllerImpl(
    private val useCase: _root_ide_package_.com.github.lolstats.core.domain.user.usecase.CreateUserUseCase
) : CreateUserController {
    override fun create(req: CreateUserRequest): UserResponse {
        val createdUser = useCase.createUser(req)

        return UserResponse.from(createdUser)
    }
}
