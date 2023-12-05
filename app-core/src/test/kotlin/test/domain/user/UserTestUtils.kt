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

object FakerInstance {
    val instance: Faker = Faker()
}
fun randomCreateUserMessage(
    password: String = FakerInstance.instance.internet().password(),
    role: Role = Role.values().random(),
    nickname: String = FakerInstance.instance.name().fullName(),
    email: String = FakerInstance.instance.internet().emailAddress(),
    phoneNumber: String = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
    zipCode: String = FakerInstance.instance.address().zipCode(),
    streetAdr: String = FakerInstance.instance.address().streetAddress(),
    detailAdr: String = FakerInstance.instance.address().secondaryAddress(),
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
    password: String? = FakerInstance.instance.internet().password(),
    role: Role? = Role.values().random(),
    nickname: String? = FakerInstance.instance.name().fullName(),
    email: String? = FakerInstance.instance.internet().emailAddress(),
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
