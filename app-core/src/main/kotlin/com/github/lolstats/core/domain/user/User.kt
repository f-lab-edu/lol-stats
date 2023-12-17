/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.domain.user

import com.github.lolstats.core.domain.SoftDeletable
import com.github.lolstats.core.domain.user.vo.UserModel
import java.time.Instant
import java.util.*

/**
 * @since 2021-08-10
 */
interface User : SoftDeletable {
    val id: UUID

    val nickname: String

    val email: String

    val phoneNumber: String

    val registeredAt: Instant

    val lastActiveAt: Instant

    companion object {
        const val LENGTH_NAME_MIN = 2
        const val LENGTH_NAME_MAX = 64
        const val LENGTH_EMAIL_MAX = 64
        const val LENGTH_PHONE_NUMBER_MAX = 13

        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various User creation cases
        fun create(
            id: UUID = UUID.randomUUID(),
            nickname: String,
            email: String,
            phoneNumber: String,
            registeredAt: Instant? = null,
            lastActiveAt: Instant? = null,
            deleted: Boolean = false
        ): User = UserModel.create(
            id = id,
            nickname = nickname,
            email = email,
            phoneNumber = phoneNumber,
            registeredAt = registeredAt,
            lastActiveAt = lastActiveAt,
            deleted = deleted
        )
    }
}
