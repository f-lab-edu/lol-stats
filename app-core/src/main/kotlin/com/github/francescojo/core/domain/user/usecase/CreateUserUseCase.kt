/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.core.domain.user.usecase

import com.github.francescojo.core.annotation.UseCase
import com.github.francescojo.core.domain.user.Role
import com.github.francescojo.core.domain.user.User
import com.github.francescojo.core.domain.user.exception.SameEmailUserAlreadyExistException
import com.github.francescojo.core.domain.user.exception.SameNicknameUserAlreadyExistException
import com.github.francescojo.core.domain.user.repository.writable.UserRepository

/**
 * @since 2021-08-10
 */
interface CreateUserUseCase {

    fun createUser(message: CreateUserMessage): User

    interface CreateUserMessage {
        val password: String
        val role: Role
        val nickname: String
        val email: String
        val phoneNumber:String
        val zipCode: String?
        val streetAdr: String?
        val detailAdr: String?
    }

    companion object {
        fun newInstance(
            userRepository: UserRepository,
            passwordEncoderService: PasswordEncoderService
        ): CreateUserUseCase = CreateUserUseCaseImpl(
            userRepository,
            passwordEncoderService
        )
    }
}

@UseCase
internal class CreateUserUseCaseImpl(
    private val users: UserRepository,
    private val passwordEncoderService: PasswordEncoderService,
) : CreateUserUseCase {
    override fun createUser(message: CreateUserUseCase.CreateUserMessage): User {
        users.findByNickname(message.nickname)?.let { throw SameNicknameUserAlreadyExistException(message.nickname) }
        users.findByEmail(message.email)?.let { throw SameEmailUserAlreadyExistException(message.email) }
//        users.findByPhoneNumber(message.phoneNumber)?.let {
//            throw SamePhoneNumberUserAlreadyExistException(message.phoneNumber)
//        }
        val hashPassword = passwordEncoderService.encode(message.password)
        val user = User.create(
            password = hashPassword,
            role = message.role,
            nickname = message.nickname,
            email = message.email,
            phoneNumber = message.phoneNumber,
            zipCode = message.zipCode,
            streetAdr = message.streetAdr,
            detailAdr = message.detailAdr
        )

        return users.save(user)
    }
}
interface PasswordEncoderService {
    fun encode(password: String): String
}
