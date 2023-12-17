/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.domain.user.usecase

import com.github.lolstats.core.annotation.UseCase
import com.github.lolstats.core.domain.user.User
import com.github.lolstats.core.domain.user.exception.UserByIdNotFoundException
import com.github.lolstats.core.domain.user.repository.UserReadonlyRepository
import java.util.*

/**
 * @since 2021-08-10
 */
interface FindUserUseCase {
    fun getUserById(id: UUID): User = findUserById(id) ?: throw UserByIdNotFoundException(
        id
    )

    fun findUserById(id: UUID): User?

    companion object {
        fun newInstance(
            userReadonlyRepository: UserReadonlyRepository
        ): FindUserUseCase = FindUserUseCaseImpl(
            userReadonlyRepository
        )
    }
}

@UseCase
internal class FindUserUseCaseImpl(
    private val users: UserReadonlyRepository
) : FindUserUseCase {
    override fun findUserById(id: UUID): User? {
        return users.findByUuid(id)
    }
}
