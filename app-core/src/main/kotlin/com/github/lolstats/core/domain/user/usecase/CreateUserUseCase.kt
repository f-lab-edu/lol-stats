/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.domain.user.usecase

import com.github.lolstats.core.annotation.UseCase
import com.github.lolstats.core.domain.user.User
import com.github.lolstats.core.domain.user.exception.SameEmailUserAlreadyExistException
import com.github.lolstats.core.domain.user.exception.SameNicknameUserAlreadyExistException
import com.github.lolstats.core.domain.user.exception.SamePhoneNumberUserAlreadyExistException
import com.github.lolstats.core.domain.user.repository.writable.UserRepository

/**
 * @since 2021-08-10
 */
interface CreateUserUseCase {
    fun createUser(message: CreateUserMessage): User

    interface CreateUserMessage {
        val nickname: String
        val email: String
        val phoneNumber:String
    }

    companion object {
        fun newInstance(
            userRepository: UserRepository
        ): CreateUserUseCase = CreateUserUseCaseImpl(
                userRepository
        )
    }
}

@UseCase
internal class CreateUserUseCaseImpl(
    private val users: UserRepository
) : CreateUserUseCase {
    override fun createUser(message: CreateUserUseCase.CreateUserMessage): User {
        users.findByNickname(message.nickname)?.let { throw SameNicknameUserAlreadyExistException(message.nickname) }
        users.findByEmail(message.email)?.let { throw com.github.lolstats.core.domain.user.exception.SameEmailUserAlreadyExistException(message.email) }
        users.findByPhoneNumber(message.phoneNumber)?.let {
            throw com.github.lolstats.core.domain.user.exception.SamePhoneNumberUserAlreadyExistException(message.phoneNumber)
        }

        val user = com.github.lolstats.core.domain.user.User.create(
            nickname = message.nickname,
            email = message.email,
            phoneNumber = message.phoneNumber,
        )

        return users.save(user)
    }
}
