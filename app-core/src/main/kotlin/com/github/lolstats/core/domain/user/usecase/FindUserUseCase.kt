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
    fun getUserById(id: UUID): com.github.lolstats.core.domain.user.User = findUserById(id) ?: throw com.github.lolstats.core.domain.user.exception.UserByIdNotFoundException(id)

    fun findUserById(id: UUID): com.github.lolstats.core.domain.user.User?

    companion object {
        fun newInstance(
            userReadonlyRepository: com.github.lolstats.core.domain.user.repository.UserReadonlyRepository
        ): FindUserUseCase = FindUserUseCaseImpl(
            userReadonlyRepository
        )
    }
}

@com.github.lolstats.core.annotation.UseCase
internal class FindUserUseCaseImpl(
    private val users: com.github.lolstats.core.domain.user.repository.UserReadonlyRepository
) : FindUserUseCase {
    override fun findUserById(id: UUID): com.github.lolstats.core.domain.user.User? {
        return users.findByUuid(id)
    }
}
