/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.francescojo.endpoint.v1.user.get

import com.github.francescojo.core.domain.user.usecase.FindUserUseCase
import com.github.francescojo.core.exception.external.WrongInputException
import com.github.francescojo.endpoint.v1.user.GetUserController
import com.github.francescojo.endpoint.v1.user.common.AuthenticationResponse
import com.github.francescojo.endpoint.v1.user.common.UserResponse
import com.github.francescojo.lib.jwt.JwtTokenUtil
import com.github.francescojo.security.CustomUserDetailsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController
import java.util.*


/**
 * @since 2021-08-10
 */
@RestController
internal class GetUserControllerImpl(
    private val userDetailsService: CustomUserDetailsService,
    private val jwtTokenUtil: JwtTokenUtil,
    private val useCase: FindUserUseCase,
    private val authenticationManager: AuthenticationManager
) : GetUserController {
    override fun get(id: UUID): UserResponse {
        val user = useCase.getUserById(id)

        return UserResponse.from(user)
    }

    override fun login(req: GetUserRequest): AuthenticationResponse {
        if (req.isEmpty()) {
            throw WrongInputException("null")
        }
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(req.email, req.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        // JWT 토큰 생성
        val userDetails = userDetailsService.loadUserByUsername(req.email!!)
        val token = jwtTokenUtil.generateToken(userDetails)
            ?: throw IllegalAccessException("Failed to generate JWT Token")

        val refreshToken = jwtTokenUtil.generateRefreshToken(userDetails)
            ?: throw IllegalAccessException("Failed to generate Refresh Token")

        return AuthenticationResponse(token, refreshToken)
    }
}
