/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.endpoint.v1.user

import com.github.francescojo.endpoint.v1.user.create.CreateUserRequest
import com.github.francescojo.endpoint.v1.user.edit.EditUserRequest
import com.github.javafaker.Faker
import java.util.*

fun CreateUserRequest.Companion.random(
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    phoneNumber: String = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
    zipCode: String = Faker().address().zipCode(),
    streetAdr: String = Faker().address().streetAddress(),
    detailAdr: String = Faker().address().secondaryAddress(),
) = CreateUserRequest(
    nickname = nickname,
    email = email,
    phoneNumber = phoneNumber,
    zipCode = zipCode,
    streetAdr = streetAdr,
    detailAdr =detailAdr
)

fun EditUserRequest.Companion.random(
    nickname: String? = Faker().name().fullName(),
    email: String? = Faker().internet().emailAddress(),
    phoneNumber: String? = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
) = EditUserRequest(
    nickname = nickname,
    email = email,
    phoneNumber = phoneNumber
)
