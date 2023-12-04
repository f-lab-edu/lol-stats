package com.github.francescojo.endpoint.v1.user.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
data class AuthenticationResponse(
    @JsonProperty
    @JsonPropertyDescription(DESC_ACCESSTOKEN)
    val accessToken: String,
    @JsonProperty
    @JsonPropertyDescription(DESC_REFRESHTOKEN)
    val refreshToken: String,
)  {
    // Comparing Instants below microseconds scale is not very useful in user scenarios, so we cut off the scales.
    override fun equals(other: Any?): Boolean {
        return other is AuthenticationResponse &&
                accessToken == other.accessToken &&
                refreshToken == other.refreshToken
    }
    override fun hashCode(): Int {
        return super.hashCode()
    }
    companion object {
        const val DESC_ACCESSTOKEN = ""
        const val DESC_REFRESHTOKEN = ""

        fun from(accessToken: String, refreshToken: String): () -> AuthenticationResponse = {
            AuthenticationResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
}