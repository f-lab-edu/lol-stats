package com.github.francescojo.security

import com.github.francescojo.lib.jwt.JwtTokenUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
//private const val KEY_LENGTH = 7
//@Component
//class JwtAuthenticationFilter(
//    private val jwtTokenUtil: JwtTokenUtil,
//    private val userDetailsService: UserDetailsService,
//) : OncePerRequestFilter() {
//    private val keyLength: Int = KEY_LENGTH
//
//    @Throws(ServletException::class, IOException::class)
//    override fun doFilterInternal(
//        @NonNull request: HttpServletRequest,
//        @NonNull response: HttpServletResponse,
//        @NonNull filterChain: FilterChain
//    ) {
//        if (request.servletPath.contains("/api/v1/login") || request.servletPath.contains("/api/v1/users")) {
//            filterChain.doFilter(request, response)
//            return
//        }
//        val authHeader = request.getHeader("Authorization")
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response)
//            return
//        }
//        val jwt: String = authHeader.substring(keyLength)
//        val userEmail = jwtTokenUtil.extractUsername(jwt)
//        if (SecurityContextHolder.getContext().authentication == null) {
//            val userDetails = userDetailsService.loadUserByUsername(userEmail)
//            if (jwtTokenUtil.isTokenValid(jwt, userDetails)) {
//                val authToken = UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.authorities
//                )
//                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
//                SecurityContextHolder.getContext().authentication = authToken
//            }
//        }
//        filterChain.doFilter(request, response)
//    }
//}
