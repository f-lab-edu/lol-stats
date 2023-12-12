/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.endpoint.v1.user.create

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.lolstats.core.domain.user.User
import com.github.lolstats.core.domain.user.usecase.CreateUserUseCase
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * @since 2021-08-10
 */
@JsonDeserialize
data class CreateUserRequest(
    @field:NotEmpty
    @field:Size(min = com.github.lolstats.core.domain.user.User.LENGTH_NAME_MIN, max = com.github.lolstats.core.domain.user.User.LENGTH_NAME_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_NAME)
    override val nickname: String,

    @field:NotEmpty
    @field:Email
    @field:Size(max = com.github.lolstats.core.domain.user.User.LENGTH_EMAIL_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_EMAIL)
    override val email: String,

    @field:NotEmpty
    @field:Size(max = com.github.lolstats.core.domain.user.User.LENGTH_PHONE_NUMBER_MAX)
    @Pattern(regexp = "(\\d{2,3})-(\\d{3,4})-(\\d{4})\$",message = "must be a well-formed phone number")
    @JsonProperty
    @JsonPropertyDescription(DESC_PHONE_NUMBER)
    override val phoneNumber: String,
): _root_ide_package_.com.github.lolstats.core.domain.user.usecase.CreateUserUseCase.CreateUserMessage {
    companion object {
        const val DESC_NAME = ""
        const val DESC_EMAIL = ""
        const val DESC_PHONE_NUMBER = ""
    }
}
