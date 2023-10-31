/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.endpoint.v1.user.create

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.francescojo.core.domain.user.User
import com.github.francescojo.core.domain.user.usecase.CreateUserUseCase
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
    @field:Size(min = User.LENGTH_NAME_MIN, max = User.LENGTH_NAME_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_NAME)
    override val nickname: String,

    @field:NotEmpty
    @field:Email
    @field:Size(max = User.LENGTH_EMAIL_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_EMAIL)
    override val email: String,

    @field:NotEmpty
    @field:Size(max = User.LENGTH_PHONE_NUMBER_MAX)
    @Pattern(regexp = "(\\d{2,3})-(\\d{3,4})-(\\d{4})\$",message = "must be a well-formed phone number")
    @JsonProperty
    @JsonPropertyDescription(DESC_PHONE_NUMBER)
    override val phoneNumber: String,

    override val zipCode: String?,
    override val streetAdr: String?,
    override val detailAdr: String?,
): CreateUserUseCase.CreateUserMessage {
    companion object {
        const val DESC_NAME = ""
        const val DESC_EMAIL = ""
        const val DESC_PHONE_NUMBER = ""
    }
}
