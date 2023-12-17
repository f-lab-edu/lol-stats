/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.domain.user.usecase

import com.github.lolstats.core.annotation.UseCase
import com.github.lolstats.core.domain.user.User
import com.github.lolstats.core.domain.user.exception.UserByIdNotFoundException
import com.github.lolstats.core.domain.user.repository.writable.UserRepository
import com.github.lolstats.core.domain.user.vo.UserModel
import java.util.*

/**
 * @since 2021-08-10
 */
interface DeleteUserUseCase {
    fun deleteUserById(id: UUID): User

    companion object {
        fun newInstance(
            userRepository: UserRepository
        ): DeleteUserUseCase = DeleteUserUseCaseImpl(
            userRepository
        )
    }
}

@UseCase
internal class DeleteUserUseCaseImpl(
    private val users: UserRepository
) : DeleteUserUseCase {
    override fun deleteUserById(id: UUID): User {
        val existingUser = users.findByUuid(id) ?: throw UserByIdNotFoundException(
            id
        )

        return users.save(UserModel.from(existingUser).delete())
    }
}
