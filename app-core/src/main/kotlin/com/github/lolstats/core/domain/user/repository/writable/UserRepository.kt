/*
 * kopringboot-multimodule-template
 * Distributed under CC BY-NC-SA
 */
package com.github.lolstats.core.domain.user.repository.writable

/**
 * @since 2021-08-10
 */
interface UserRepository : com.github.lolstats.core.domain.user.repository.UserReadonlyRepository {
    fun save(user: com.github.lolstats.core.domain.user.User): com.github.lolstats.core.domain.user.User

    companion object {
        const val NAME = "com.github.francescojo.core.domain.user.UserRepository"
    }
}
