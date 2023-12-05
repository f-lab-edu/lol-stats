/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.core.domain.user.usecase

import com.github.francescojo.core.annotation.UseCase
import com.github.francescojo.core.domain.user.User
import com.github.francescojo.core.domain.user.exception.UserByEmailNotFoundException
import com.github.francescojo.core.domain.user.exception.UserByIdNotFoundException
import com.github.francescojo.core.domain.user.repository.UserReadonlyRepository
import java.util.*

/**
 * @since 2021-08-10
 */
interface FindUserUseCase {
    interface FindUserMessage{
        val email: String?
        val password: String?
    }
    fun getUserById(id: UUID): User = findUserById(id) ?: throw UserByIdNotFoundException(id)

    fun findUserById(id: UUID): User?

    fun getUserByEmail(email: String): User = findUserByEmail(email) ?: throw UserByEmailNotFoundException(email)

    fun findUserByEmail(email: String): User?

    fun findUserByEmail(email: String, password: String): User?
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

    override fun findUserByEmail(email: String): User? {
        return users.findByEmail(email)
    }

    override fun findUserByEmail(email: String,password: String): User?{
        //password
        return users.findByEmail(email)
    }
}
