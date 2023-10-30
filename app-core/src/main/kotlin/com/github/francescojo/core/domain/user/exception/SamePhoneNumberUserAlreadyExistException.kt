package com.github.francescojo.core.domain.user.exception

import com.github.francescojo.core.exception.ErrorCodes
import com.github.francescojo.core.exception.ExternalException

class SamePhoneNumberUserAlreadyExistException (
    val phoneNumber: String,
    override val message: String = "User with phoneNumber('$phoneNumber') already exists.",
    override val cause: Throwable? = null
) : ExternalException(ErrorCodes.USER_BY_PHONE_NUMBER_DUPLICATED, message, cause) {
    override fun messageArguments(): Collection<String> = setOf(phoneNumber)
}
