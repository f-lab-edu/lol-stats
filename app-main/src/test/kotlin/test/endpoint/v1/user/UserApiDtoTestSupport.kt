/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.endpoint.v1.user

import com.github.javafaker.Faker
import com.github.lolstats.endpoint.v1.user.create.CreateUserRequest
import com.github.lolstats.endpoint.v1.user.edit.EditUserRequest

fun CreateUserRequest.Companion.random(
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress()
) = CreateUserRequest(
    nickname = nickname,
    email = email
)

fun EditUserRequest.Companion.random(
    nickname: String? = Faker().name().fullName(),
    email: String? = Faker().internet().emailAddress()
) = EditUserRequest(
    nickname = nickname,
    email = email
)
