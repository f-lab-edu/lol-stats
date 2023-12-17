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
import com.github.lolstats.core.domain.user.exception.UserByIdNotFoundException
import com.github.lolstats.core.domain.user.repository.writable.UserRepository
import com.github.lolstats.core.domain.user.vo.UserModel
import java.util.*

/**
 * @since 2021-08-10
 */
interface EditUserUseCase {
    fun editUser(id: UUID, message: EditUserMessage): com.github.lolstats.core.domain.user.User

    interface EditUserMessage {
        val nickname: String?
        val email: String?
        val phoneNumber: String?
    }

    companion object {
        fun newInstance(
            userRepository: com.github.lolstats.core.domain.user.repository.writable.UserRepository
        ): EditUserUseCase = EditUserUseCaseImpl(
            userRepository
        )
    }
}

@com.github.lolstats.core.annotation.UseCase
internal class EditUserUseCaseImpl(
    private val users: com.github.lolstats.core.domain.user.repository.writable.UserRepository
) : EditUserUseCase {
    override fun editUser(id: UUID, message: EditUserUseCase.EditUserMessage): com.github.lolstats.core.domain.user.User {
        val existingUser = users.findByUuid(id) ?: throw com.github.lolstats.core.domain.user.exception.UserByIdNotFoundException(id)

        message.nickname.takeIf { !it.isNullOrEmpty() }?.let { nickname ->
            users.findByNickname(nickname)?.let { throw com.github.lolstats.core.domain.user.exception.SameNicknameUserAlreadyExistException(nickname) }
        }
        message.email.takeIf { !it.isNullOrEmpty() }?.let { email ->
            users.findByEmail(email)?.let { throw com.github.lolstats.core.domain.user.exception.SameEmailUserAlreadyExistException(email) }
        }
        message.phoneNumber.takeIf { !it.isNullOrEmpty() }?.let { phoneNumber ->
            users.findByPhoneNumber(phoneNumber)?.let { throw com.github.lolstats.core.domain.user.exception.SamePhoneNumberUserAlreadyExistException(phoneNumber) }
        }

        return users.save(UserModel.from(existingUser).applyValues(message))
    }
}
