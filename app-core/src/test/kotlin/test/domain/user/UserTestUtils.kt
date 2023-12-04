/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.domain.user

import com.github.francescojo.core.domain.user.Role
import com.github.francescojo.core.domain.user.usecase.CreateUserUseCase
import com.github.francescojo.core.domain.user.usecase.EditUserUseCase
import com.github.javafaker.Faker
import java.util.*

fun randomCreateUserMessage(
    password: String = Faker().internet().password(),
    role: Role = Role.values().random(),
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    phoneNumber: String = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
    zipCode: String = Faker().address().zipCode(),
    streetAdr: String = Faker().address().streetAddress(),
    detailAdr: String = Faker().address().secondaryAddress(),
): CreateUserUseCase.CreateUserMessage {
    data class FakeCreateUserMessage(
        override val password: String,
        override val role: Role,
        override val nickname: String,
        override val email: String,
        override val phoneNumber: String,
        override val zipCode: String?,
        override val streetAdr: String?,
        override val detailAdr: String?
    ) : CreateUserUseCase.CreateUserMessage

    return FakeCreateUserMessage(
        password = password,
        role = role,
        nickname = nickname,
        email = email,
        phoneNumber = phoneNumber,
        zipCode = zipCode,
        streetAdr = streetAdr,
        detailAdr = detailAdr,
    )
}

fun randomEditUserMessage(
    password: String? = Faker().internet().password(),
    role: Role? = Role.values().random(),
    nickname: String? = Faker().name().fullName(),
    email: String? = Faker().internet().emailAddress(),
    phoneNumber: String? = Faker(Locale.KOREAN).phoneNumber().phoneNumber()
): EditUserUseCase.EditUserMessage {
    data class FakeEditUserMessage(
        override val password: String?,
        override val role: Role?,
        override val nickname: String?,
        override val email: String?,
        override val phoneNumber: String?
    ) : EditUserUseCase.EditUserMessage

    return FakeEditUserMessage(
        password = password,
        role = role,
        nickname = nickname,
        email = email,
        phoneNumber = phoneNumber
    )
}
