package com.github.francescojo.security

import com.github.francescojo.core.domain.user.usecase.FindUserUseCase
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val useCase: FindUserUseCase
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = useCase.getUserByEmail(username)
        return CustomUserDetails(user)
    }
}
