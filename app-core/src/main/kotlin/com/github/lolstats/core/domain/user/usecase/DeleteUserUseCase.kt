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
    fun deleteUserById(id: UUID): com.github.lolstats.core.domain.user.User

    companion object {
        fun newInstance(
            userRepository: com.github.lolstats.core.domain.user.repository.writable.UserRepository
        ): DeleteUserUseCase = DeleteUserUseCaseImpl(
            userRepository
        )
    }
}

@com.github.lolstats.core.annotation.UseCase
internal class DeleteUserUseCaseImpl(
    private val users: com.github.lolstats.core.domain.user.repository.writable.UserRepository
) : DeleteUserUseCase {
    override fun deleteUserById(id: UUID): com.github.lolstats.core.domain.user.User {
        val existingUser = users.findByUuid(id) ?: throw com.github.lolstats.core.domain.user.exception.UserByIdNotFoundException(id)

        return users.save(UserModel.from(existingUser).delete())
    }
}
