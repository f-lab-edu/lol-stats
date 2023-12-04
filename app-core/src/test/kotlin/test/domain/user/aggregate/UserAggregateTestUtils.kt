/*
 * kopringboot-multimodule-template
 * Distributed under CC BY-NC-SA
 */
package test.domain.user.aggregate

import com.github.francescojo.core.domain.user.Role
import com.github.francescojo.core.domain.user.User
import com.github.javafaker.Faker
import java.time.Instant
import java.util.*

fun randomUser(
    id: UUID = UUID.randomUUID(),
    password: String = Faker().internet().password(),
    role: Role = Role.USER,
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    phoneNumber: String = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
    registeredAt: Instant = Instant.now(),
    lastActiveAt: Instant = Instant.now(),
    deleted: Boolean = false
) = User.create(
    id = id,
    password = password,
    role = role,
    nickname = nickname,
    email = email,
    phoneNumber = phoneNumber,
    registeredAt = registeredAt,
    lastActiveAt = lastActiveAt,
    deleted = deleted
)
