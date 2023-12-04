/*
 * kopringboot-multimodule-template
 * Distributed under CC BY-NC-SA
 */
package test.domain.user

import com.github.francescojo.core.domain.user.Role
import com.github.francescojo.core.jdbc.user.UserEntity
import com.github.javafaker.Faker
import test.domain.user.aggregate.randomUser
import java.time.Instant
import java.util.*

object FakerInstance {
    val instance: Faker = Faker()
}
internal fun randomUserEntity(
    id: UUID = UUID.randomUUID(),
    password: String = FakerInstance.instance.internet().password(),
    role: Role = Role.USER,
    nickname: String = FakerInstance.instance.name().fullName(),
    email: String = FakerInstance.instance.internet().emailAddress(),
    phoneNumber: String = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
    registeredAt: Instant = Instant.now(),
    lastActiveAt: Instant = Instant.now(),
    deleted: Boolean = false
): UserEntity = UserEntity.from(
    randomUser(
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
)
