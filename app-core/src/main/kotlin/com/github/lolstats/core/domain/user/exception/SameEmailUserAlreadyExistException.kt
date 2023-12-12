/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.domain.user.exception

import com.github.lolstats.core.exception.ErrorCodes
import com.github.lolstats.core.exception.ExternalException

/**
 * @since 2021-08-10
 */
class SameEmailUserAlreadyExistException(
    val email: String,
    override val message: String = "User with email('$email') already exists.",
    override val cause: Throwable? = null
) : ExternalException(ErrorCodes.USER_BY_EMAIL_DUPLICATED, message, cause) {
    override fun messageArguments(): Collection<String> = setOf(email)
}
