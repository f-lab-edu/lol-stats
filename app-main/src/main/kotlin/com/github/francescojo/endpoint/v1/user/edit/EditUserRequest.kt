/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.endpoint.v1.user.edit

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.francescojo.core.domain.user.Role
import com.github.francescojo.core.domain.user.User
import com.github.francescojo.core.domain.user.usecase.EditUserUseCase
import com.github.francescojo.endpoint.v1.user.create.CreateUserRequest
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * @since 2021-08-10
 */
@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EditUserRequest(
    @field:NotEmpty
    @field:Size(min = User.LENGTH_PASSWORD_MIN, max = User.LENGTH_PASSWORD_MAX)
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$)[0-9a-zA-Z@#$%^&+=]$",
        message = "Password must contain at least one digit, one lowercase, no spaces, " +
                "lowercase, uppercase and special characters (@#$%^&+= only)")
    @JsonProperty
    @JsonPropertyDescription(CreateUserRequest.DESC_PASSWORD)
    override val password: String?,

    @JsonProperty
    @JsonPropertyDescription(CreateUserRequest.DESC_ROLE)
    override val role: Role?,

    @field:Nullable
    @field:Size(min = User.LENGTH_NAME_MIN, max = User.LENGTH_NAME_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_NAME)
    override val nickname: String?,

    @field:Email
    @field:Nullable
    @field:Size(max = User.LENGTH_EMAIL_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_EMAIL)
    override val email: String?,

    @Pattern(regexp = "^01\\d-(\\d{3,4})-(\\d{4})\$",message = "must be a well-formed phone number")
    @field:Nullable
    @field:Size(max = User.LENGTH_PHONE_NUMBER_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_PHONE_NUMBER)
    override val phoneNumber: String?
) : EditUserUseCase.EditUserMessage {
    fun isEmpty() = nickname.isNullOrEmpty() && email.isNullOrEmpty() && phoneNumber.isNullOrEmpty()

    companion object {
        const val DESC_NAME = ""
        const val DESC_EMAIL = ""
        const val DESC_PHONE_NUMBER = ""
    }

}
