/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.domain.user

import com.github.javafaker.Faker
import com.github.lolstats.core.domain.user.usecase.CreateUserUseCase
import com.github.lolstats.core.domain.user.usecase.EditUserUseCase
import java.util.*

fun randomCreateUserMessage(
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    phoneNumber: String = Faker(Locale.KOREAN).phoneNumber().phoneNumber()
): CreateUserUseCase.CreateUserMessage {
    data class FakeCreateUserMessage(
        override val nickname: String,
        override val email: String,
        override val phoneNumber: String,
    ) : CreateUserUseCase.CreateUserMessage

    return FakeCreateUserMessage(
        nickname = nickname,
        email = email,
        phoneNumber = phoneNumber
    )
}

fun randomEditUserMessage(
    nickname: String? = Faker().name().fullName(),
    email: String? = Faker().internet().emailAddress()
): EditUserUseCase.EditUserMessage {
    data class FakeEditUserMessage(
        override val nickname: String?,
        override val email: String?
    ) : EditUserUseCase.EditUserMessage

    return FakeEditUserMessage(
        nickname = nickname,
        email = email
    )
}
