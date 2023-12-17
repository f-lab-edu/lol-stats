/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.small.domain.user

import com.github.lolstats.core.domain.user.exception.SameEmailUserAlreadyExistException
import com.github.lolstats.core.domain.user.exception.SameNicknameUserAlreadyExistException
import com.github.lolstats.core.domain.user.exception.SamePhoneNumberUserAlreadyExistException
import com.github.lolstats.core.domain.user.repository.writable.UserRepository
import com.github.lolstats.core.domain.user.usecase.CreateUserUseCase
import com.github.lolstats.lib.annotation.SmallTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import test.domain.user.aggregate.randomUser
import test.domain.user.randomCreateUserMessage
import java.util.*

/**
 * @since 2021-08-10
 */
@SmallTest
class CreateUserUseCaseSpec {
    private lateinit var sut: _root_ide_package_.com.github.lolstats.core.domain.user.usecase.CreateUserUseCase
    private lateinit var userRepository: com.github.lolstats.core.domain.user.repository.writable.UserRepository

    @BeforeEach
    fun setup() {
        userRepository = mock()
        sut = _root_ide_package_.com.github.lolstats.core.domain.user.usecase.CreateUserUseCase.newInstance(userRepository)

        `when`(userRepository.save(any())).thenAnswer { return@thenAnswer it.arguments[0] }
    }

    @DisplayName("An user object that fully represents message, is created")
    @Test
    fun userIsCreated() {
        // given:
        val message = randomCreateUserMessage()

        // when:
        val createdUser = sut.createUser(message)

        // then:
        assertAll(
            { assertThat(createdUser.nickname, `is`(message.nickname)) },
            { assertThat(createdUser.email, `is`(message.email)) },
        )
    }

    @DisplayName("Nickname must not be duplicated")
    @Test
    fun errorIfNicknameIsDuplicated() {
        // given:
        val message = randomCreateUserMessage()

        // and:
        `when`(userRepository.findByNickname(message.nickname)).thenReturn(randomUser(nickname = message.nickname))

        // then:
        assertThrows<com.github.lolstats.core.domain.user.exception.SameNicknameUserAlreadyExistException> { sut.createUser(message) }
    }

    @DisplayName("Email must not be duplicated")
    @Test
    fun errorIfEmailIsDuplicated() {
        // given:
        val message = randomCreateUserMessage()

        // and:
        `when`(userRepository.findByEmail(message.email)).thenReturn(randomUser(email = message.email))

        // then:
        assertThrows<com.github.lolstats.core.domain.user.exception.SameEmailUserAlreadyExistException> { sut.createUser(message) }
    }

    @DisplayName("PhoneNumber must not be duplicated")
    @Test
    fun errorIfPhoneNumberIsDuplicated() {
        // given:
        val message = randomCreateUserMessage()

        // and:
        `when`(userRepository.findByPhoneNumber(message.phoneNumber)).thenReturn(randomUser(phoneNumber = message.phoneNumber))

        // then:
        assertThrows<com.github.lolstats.core.domain.user.exception.SamePhoneNumberUserAlreadyExistException> { sut.createUser(message) }
    }
}
