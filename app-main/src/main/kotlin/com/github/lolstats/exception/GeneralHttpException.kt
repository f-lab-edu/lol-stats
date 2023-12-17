/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.exception

import com.github.lolstats.core.exception.ErrorCodes
import com.github.lolstats.core.exception.ExternalException
import org.springframework.http.HttpStatusCode

/**
 * @since 2021-08-10
 */
class GeneralHttpException(
    val statusCode: HttpStatusCode,
    override val message: String = "An unhandled HTTP exception(status = ${statusCode.value()} is occurred.",
    override val cause: Throwable? = null
) : ExternalException(ErrorCodes.GENERAL_HTTP_EXCEPTION, message, cause) {
    override fun messageArguments(): Collection<String> = setOf(statusCode.value().toString())
}
