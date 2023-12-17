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
        ): CreateUserUseCase =
            CreateUserUseCaseImpl(
                userRepository
            )
    }
}

@UseCase
internal class CreateUserUseCaseImpl(
    private val users: UserRepository
) : CreateUserUseCase {
    override fun createUser(message: CreateUserUseCase.CreateUserMessage): User {
        users.findByNickname(message.nickname)?.let {
            throw SameNicknameUserAlreadyExistException(message.nickname)
        }
        users.findByEmail(message.email)?.let {
            throw SameEmailUserAlreadyExistException(message.email)
        }
//        users.findByPhoneNumber(message.phoneNumber)?.let {
//            throw SamePhoneNumberUserAlreadyExistException(message.phoneNumber)
//        }

        val user = User.create(
            nickname = message.nickname,
            phoneNumber = "",
            email = message.email
        )

        return users.save(user)
    }
}
