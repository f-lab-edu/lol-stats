package com.github.francescojo.security

import com.github.francescojo.core.domain.user.exception.UserByEmailNotFoundException
import com.github.francescojo.core.domain.user.usecase.FindUserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
@Service
class CustomUserDetailsService(
) : UserDetailsService {
    @Autowired
    private val user: FindUserUseCase? = null

    override fun loadUserByUsername(username: String): UserDetails {
        val user = user?.getUserByEmail(username)?:throw UserByEmailNotFoundException(username)
        return CustomUserDetails(user)
    }
}
