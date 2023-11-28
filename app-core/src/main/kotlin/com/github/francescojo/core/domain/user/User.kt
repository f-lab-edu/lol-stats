/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.core.domain.user

import com.github.francescojo.core.domain.SoftDeletable
import com.github.francescojo.core.domain.user.vo.UserModel
import java.time.Instant
import java.util.*

/**
 * @since 2021-08-10
 */
interface User :  SoftDeletable {
    val id: UUID

    val password: String

    val role: Role

    val nickname: String

    val email: String

    val address: Address?

    val phoneNumber: String

    val registeredAt: Instant

    val lastActiveAt: Instant

    companion object {
        const val LENGTH_PASSWORD_MIN = 8
        const val LENGTH_PASSWORD_MAX = 16
        const val LENGTH_NAME_MIN = 2
        const val LENGTH_NAME_MAX = 64
        const val LENGTH_EMAIL_MAX = 64
        const val LENGTH_PHONE_NUMBER_MAX = 13

        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various User creation cases
        fun create(
            id: UUID = UUID.randomUUID(),
            password: String,
            role: Role,
            nickname: String,
            email: String,
            phoneNumber: String,
            registeredAt: Instant? = null,
            lastActiveAt: Instant? = null,
            deleted: Boolean = false,
            zipCode: String? = null,
            streetAdr: String? = null,
            detailAdr: String? = null,
        ): User {
            val address = if (zipCode != null && streetAdr != null) {
                object : Address {
                    override val zipCode: String = zipCode
                    override val streetAdr: String  = streetAdr
                    override val detailAdr: String? = detailAdr
                }
            } else null
            return UserModel.create(
                id = id,
                password = password,
                role = role,
                nickname = nickname,
                email = email,
                phoneNumber = phoneNumber,
                registeredAt = registeredAt,
                lastActiveAt = lastActiveAt,
                deleted = deleted,
                address = address
            )
        }
    }
}
