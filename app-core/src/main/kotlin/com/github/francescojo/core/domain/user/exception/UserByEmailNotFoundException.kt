/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.core.domain.user.exception

import com.github.francescojo.core.exception.ErrorCodes
import com.github.francescojo.core.exception.external.MalformedInputException

class UserByEmailNotFoundException(
    val email: String,
    override val message: String = "User('$email') is not found.",
    override val cause: Throwable? = null
) : MalformedInputException(ErrorCodes.USER_NOT_FOUND, message, cause) {
    override fun messageArguments(): Collection<String> = setOf(email)
}
