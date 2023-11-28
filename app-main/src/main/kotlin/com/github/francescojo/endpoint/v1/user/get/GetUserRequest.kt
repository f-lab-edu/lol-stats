package com.github.francescojo.endpoint.v1.user.get

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.francescojo.core.domain.user.User
import com.github.francescojo.core.domain.user.usecase.FindUserUseCase
import com.github.francescojo.endpoint.v1.user.edit.EditUserRequest
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
data class GetUserRequest(
    @field:Email
    @field:Nullable
    @field:Size(max = User.LENGTH_EMAIL_MAX)
    @JsonProperty
    @JsonPropertyDescription(EditUserRequest.DESC_EMAIL)
    override val email: String?,

    @field:Nullable
    @field:Size(max = User.LENGTH_PASSWORD_MAX)
    @JsonProperty
    @JsonPropertyDescription(EditUserRequest.DESC_EMAIL)
    override val password: String?,
) : FindUserUseCase.FindUserMessage {
    fun isEmpty() = email.isNullOrEmpty() || password.isNullOrEmpty()

    companion object {
        const val DESC_EMAIL = ""
        const val DESC_PASSWORD = ""
    }
}

