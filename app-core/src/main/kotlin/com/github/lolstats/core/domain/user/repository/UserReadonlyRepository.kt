/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.domain.user.repository

import com.github.lolstats.core.domain.user.User
import java.util.*

/**
 * @since 2021-08-10
 */
interface UserReadonlyRepository {
    fun findByUuid(uuid: UUID): User?

    fun findByNickname(nickname: String): User?

    fun findByEmail(email: String): User?

    fun findByPhoneNumber(phoneNumber: String): User?

    companion object {
        const val NAME = "com.github.lolstats.core.domain.user.UserReadonlyRepository"
    }
}
