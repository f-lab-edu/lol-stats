/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.endpoint.v1.user.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.github.lolstats.core.domain.user.User
import com.github.lolstats.lib.util.truncateToSeconds
import java.time.Instant
import java.util.*

/**
 * @since 2021-08-10
 */
@JsonSerialize
data class UserResponse(
    @JsonProperty
    @JsonPropertyDescription(DESC_ID)
    val id: UUID,

    @JsonProperty
    @JsonPropertyDescription(DESC_NICKNAME)
    val nickname: String,

    @JsonProperty
    @JsonPropertyDescription(DESC_EMAIL)
    val email: String,

    @JsonProperty
    @JsonPropertyDescription(DESC_PHONE_NUMBER)
    val phoneNumber: String,

    @JsonProperty
    @JsonPropertyDescription(DESC_REGISTERED_AT)
    val registeredAt: Instant,

    @JsonProperty
    @JsonPropertyDescription(DESC_LAST_ACTIVE_AT)
    val lastActiveAt: Instant
) {
    // Comparing Instants below microseconds scale is not very useful in user scenarios, so we cut off the scales.
    override fun equals(other: Any?): Boolean {
        return other is UserResponse &&
                id == other.id &&
                nickname == other.nickname &&
                email == other.email &&
                phoneNumber == other.phoneNumber &&
                registeredAt.truncateToSeconds() == other.registeredAt.truncateToSeconds() &&
                lastActiveAt.truncateToSeconds() == other.lastActiveAt.truncateToSeconds()
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + registeredAt.truncateToSeconds().hashCode()
        result = 31 * result + lastActiveAt.truncateToSeconds().hashCode()
        return result
    }

    companion object {
        const val DESC_ID = ""
        const val DESC_NICKNAME = ""
        const val DESC_EMAIL = ""
        const val DESC_PHONE_NUMBER = ""
        const val DESC_REGISTERED_AT = ""
        const val DESC_LAST_ACTIVE_AT = ""

        fun from(src: com.github.lolstats.core.domain.user.User) = with(src) {
            UserResponse(
                id = id,
                nickname = nickname,
                email = email,
                phoneNumber = phoneNumber,
                registeredAt = registeredAt,
                lastActiveAt = lastActiveAt
            )
        }
    }
}