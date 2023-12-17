/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.core.exception.external

import com.github.lolstats.core.exception.ErrorCodes
import com.github.lolstats.core.exception.ExternalException

/**
 * Indicates that an external input is unrecognisable.
 *
 * @since 2021-08-10
 */
open class MalformedInputException(
    override val codeBook: ErrorCodes = ErrorCodes.MALFORMED_INPUT,
    override val message: String = "Malformed input.",
    override val cause: Throwable? = null
) : ExternalException(codeBook, message, cause)
