/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.endpoint.v1.user

import com.github.francescojo.core.domain.user.Role
import com.github.francescojo.endpoint.v1.user.create.CreateUserRequest
import com.github.francescojo.endpoint.v1.user.edit.EditUserRequest
import com.github.francescojo.endpoint.v1.user.get.GetUserRequest
import com.github.javafaker.Faker
import java.util.*
fun CreateUserRequest.Companion.random(
    password: String = Faker().internet().password(),
    role: Role = Role.USER,
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    phoneNumber: String = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
    zipCode: String = Faker().address().zipCode(),
    streetAdr: String = Faker().address().streetAddress(),
    detailAdr: String = Faker().address().secondaryAddress(),
) = CreateUserRequest(
    password = password,
    role = role,
    nickname = nickname,
    email = email,
    phoneNumber = phoneNumber,
    zipCode = zipCode,
    streetAdr = streetAdr,
    detailAdr =detailAdr
)

fun EditUserRequest.Companion.random(
    password: String? = Faker().internet().password(),
    role: Role? = Role.values().random(),
    nickname: String? = Faker().name().fullName(),
    email: String? = Faker().internet().emailAddress(),
    phoneNumber: String? = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
) = EditUserRequest(
    password = password,
    role = role,
    nickname = nickname,
    email = email,
    phoneNumber = phoneNumber
)


fun GetUserRequest.Companion.random(
    email: String = Faker().internet().emailAddress(),
    password: String = Faker().internet().password(),
) = GetUserRequest(
    email = email,
    password = password,
)
