package com.github.francescojo.appconfig

import com.github.francescojo.core.domain.user.usecase.PasswordEncoderService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordEncoderServiceImpl(private val passwordEncoder: PasswordEncoder) : PasswordEncoderService {
    override fun encode(password: String): String {
        return passwordEncoder.encode(password)
    }
}