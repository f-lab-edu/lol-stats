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
object FakerInstance {
    val instance: Faker = Faker()
}
fun CreateUserRequest.Companion.random(
    password: String = FakerInstance.instance.internet().password(),
    role: Role = Role.USER,
    nickname: String = FakerInstance.instance.name().fullName(),
    email: String = FakerInstance.instance.internet().emailAddress(),
    phoneNumber: String = FakerInstance.instance.phoneNumber().phoneNumber(),
    zipCode: String = FakerInstance.instance.address().zipCode(),
    streetAdr: String = FakerInstance.instance.address().streetAddress(),
    detailAdr: String = FakerInstance.instance.address().secondaryAddress(),
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
    password: String? = FakerInstance.instance.internet().password(),
    role: Role? = Role.values().random(),
    nickname: String? = FakerInstance.instance.name().fullName(),
    email: String? = FakerInstance.instance.internet().emailAddress(),
    phoneNumber: String? = FakerInstance.instance.phoneNumber().phoneNumber(),
) = EditUserRequest(
    password = password,
    role = role,
    nickname = nickname,
    email = email,
    phoneNumber = phoneNumber
)


fun GetUserRequest.Companion.random(
    email: String = FakerInstance.instance.internet().emailAddress(),
    password: String = FakerInstance.instance.internet().password(),
) = GetUserRequest(
    email = email,
    password = password,
)
