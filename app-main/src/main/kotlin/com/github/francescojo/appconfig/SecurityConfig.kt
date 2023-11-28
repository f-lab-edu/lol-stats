package com.github.francescojo.appconfig

import com.github.francescojo.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


/**
 * Security configuration for the main application.
 *
 * @author Josh Cummings
 */
@Configuration
class SecurityConfig {
    private val authenticationProvider: AuthenticationProvider? = null
    private val jwtAuthFilter: JwtAuthenticationFilter? = null
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // @formatter:off
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests {authorize->
                authorize
                    .requestMatchers("admin/**").hasRole("ADMIN")
                    .requestMatchers("v1/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().permitAll()
            }
            .sessionManagement{session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        // @formatter:on
        return http.build()
    }
}
