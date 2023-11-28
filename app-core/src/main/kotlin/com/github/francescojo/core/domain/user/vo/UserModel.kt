/*
 * kopringboot-multimodule-template
 * Distributed under CC BY-NC-SA
 */
package com.github.francescojo.core.domain.user.vo

import com.github.francescojo.core.domain.user.Address
import com.github.francescojo.core.domain.user.Role
import com.github.francescojo.core.domain.user.User
import com.github.francescojo.core.domain.user.usecase.EditUserUseCase
import java.time.Instant
import java.util.*

/**
 * Implementation note is taken from: https://martinfowler.com/bliki/EvansClassification.html
 *
 * @since 2022-10-03
 */
internal data class UserModel(
    override val id: UUID,
    override val password: String,
    override val role: Role,
    override val nickname: String,
    override val email: String,
    override val phoneNumber: String,
    override val address: Address?,
    override val registeredAt: Instant,
    override val lastActiveAt: Instant,
    override val deleted: Boolean
) : User {

    fun applyValues(values: EditUserUseCase.EditUserMessage): User = this.copy(
        nickname = values.nickname ?: this.nickname,
        password = values.password ?: this.password,
        role = values.role ?: this.role,
        email = values.email ?: this.email,
        phoneNumber = values.phoneNumber ?: this.phoneNumber,
        lastActiveAt = Instant.now()
    )

    override fun delete(): User = this.copy(
        deleted = true,
        lastActiveAt = Instant.now()
    )

    companion object {
        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various User creation cases
        fun create(
            id: UUID = UUID.randomUUID(),
            password: String,
            role: Role,
            nickname: String,
            email: String,
            phoneNumber: String,
            address: Address? = null,
            registeredAt: Instant? = null,
            lastActiveAt: Instant? = null,
            deleted: Boolean = false,
        ): UserModel {
            val now = Instant.now()

            return UserModel(
                id = id,
                password = password,
                role = role,
                nickname = nickname,
                email = email,
                phoneNumber = phoneNumber,
                address = address,
                registeredAt = registeredAt ?: now,
                lastActiveAt = lastActiveAt ?: now,
                deleted = deleted
            )
        }

        fun from(user: User): UserModel = if (user is UserModel) {
            user
        } else {
            with(user) {
                create(
                    id = id,
                    password = password,
                    role = role,
                    nickname = nickname,
                    email = email,
                    phoneNumber = phoneNumber,
                    address = address,
                    registeredAt = registeredAt,
                    lastActiveAt = lastActiveAt,
                    deleted = deleted
                )
            }
        }
    }
}
