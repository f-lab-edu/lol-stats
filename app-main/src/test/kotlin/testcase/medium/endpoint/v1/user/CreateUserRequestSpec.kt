/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.medium.endpoint.v1.user

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.lolstats.core.domain.user.User
import com.github.lolstats.core.exception.ErrorCodes
import com.github.lolstats.endpoint.v1.ApiPathsV1
import com.github.javafaker.Faker
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import testcase.medium.ControllerMediumTestBase
import java.util.Locale
import java.util.stream.Stream

/**
 * @since 2021-08-10
 */
class CreateUserRequestSpec : ControllerMediumTestBase() {
    @ParameterizedTest(name = "Fails if it is {0} characters")
    @MethodSource("badNicknames")
    fun failIfNicknamesAreBad(
        testName: String,
        nickname: String
    ) {
        // given:
        val payload = FakeCreateUserRequest(
            nickname = nickname,
            email = Faker().internet().emailAddress(),
            phoneNumber = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
            )

        // when:
        val request = post(ApiPathsV1.USERS, payload)

        // then:
        val errorResponse = request.send().expect4xx()

        // expect:
        assertThat(ErrorCodes.from(errorResponse.code), `is`(ErrorCodes.WRONG_INPUT))
    }

    @ParameterizedTest(name = "Fails if it is {0}")
    @MethodSource("badEmails")
    fun failIfEmailsAreBad(
        testName: String,
        email: String,
    ) {
        // given:
        val payload = FakeCreateUserRequest(
            nickname = Faker().name().fullName(),
            email = email,
            phoneNumber = Faker(Locale.KOREAN).phoneNumber().phoneNumber(),
        )

        // when:
        val request = post(ApiPathsV1.USERS, payload)

        // then:
        val errorResponse = request.send().expect4xx()

        // expect:
        assertThat(ErrorCodes.from(errorResponse.code), `is`(ErrorCodes.WRONG_INPUT))
    }

    @ParameterizedTest(name = "Fails if it is {0}")
    @MethodSource("badPhoneNumber")
    fun failIfPhoneNumbersAreBad(
        testName: String,
        phoneNumber: String?,
    ) {
        // given:
        val payload = FakeCreateUserRequest(
            nickname = Faker().name().fullName(),
            email = Faker().internet().emailAddress(),
            phoneNumber = phoneNumber,
        )

        // when:
        val request = post(ApiPathsV1.USERS, payload)

        // then:
        val errorResponse = request.send().expect4xx()

        // expect:
        assertThat(ErrorCodes.from(errorResponse.code), `is`(ErrorCodes.WRONG_INPUT))
    }

    @JsonDeserialize
    private data class FakeCreateUserRequest(
        val nickname: String?,
        val email: String?,
        val phoneNumber: String?,
    )

    companion object {
        @JvmStatic
        fun badNicknames(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "shorter than ${com.github.lolstats.core.domain.user.User.LENGTH_NAME_MIN}",
                Faker().letterify("?").repeat(com.github.lolstats.core.domain.user.User.LENGTH_NAME_MIN - 1),
            ),
            Arguments.of(
                "longer than ${com.github.lolstats.core.domain.user.User.LENGTH_NAME_MAX}",
                Faker().letterify("?").repeat(com.github.lolstats.core.domain.user.User.LENGTH_NAME_MAX + 1),
            )
        )

        @JvmStatic
        fun badEmails(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "empty",
                "",
            ),
            Arguments.of(
                "not an IETF email format",
                Faker().lorem().word(),
            ),
            Arguments.of(
                "longer than ${com.github.lolstats.core.domain.user.User.LENGTH_EMAIL_MAX}",
                Faker().letterify("?").repeat(com.github.lolstats.core.domain.user.User.LENGTH_EMAIL_MAX + 1) + "@company.com",
            )
        )

        @JvmStatic
        fun badPhoneNumber(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "empty",
                "",
            ),
            Arguments.of(
                "not an Korea phone number format",
                Faker(Locale.US).phoneNumber().phoneNumber(),
            ),
            Arguments.of(
                "longer than ${com.github.lolstats.core.domain.user.User.LENGTH_PHONE_NUMBER_MAX}",
                Faker().letterify("?").repeat(com.github.lolstats.core.domain.user.User.LENGTH_PHONE_NUMBER_MAX + 1),
            )
        )
    }
}
