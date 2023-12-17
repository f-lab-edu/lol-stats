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
    fun findByUuid(uuid: UUID): com.github.lolstats.core.domain.user.User?

    fun findByNickname(nickname: String): com.github.lolstats.core.domain.user.User?

    fun findByEmail(email: String): com.github.lolstats.core.domain.user.User?

    fun findByPhoneNumber(phoneNumber: String): com.github.lolstats.core.domain.user.User?

    companion object {
        const val NAME = "com.github.lolstats.core.domain.user.UserReadonlyRepository"
    }
}
