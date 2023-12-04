package com.github.francescojo.lib.jwt


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.Date
import java.util.HashMap
import java.util.function.Function
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import javax.crypto.SecretKey


private const val JWT_EXPIRATION = 86400000L
private const val REFRESH_JWT_EXPIRATION = 604800000L
@Service
@SuppressWarnings("TooManyFunctions")
class JwtTokenUtil {

    private val secretKey: String? = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"

    private val jwtExpiration: Long = JWT_EXPIRATION

    private val refreshExpiration: Long = REFRESH_JWT_EXPIRATION

    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun generateToken(userDetails: UserDetails): String? {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(
        extraClaims: Map<String?, Any?>,
        userDetails: UserDetails
    ): String? {
        return buildToken(extraClaims, userDetails, jwtExpiration)
    }

    fun generateRefreshToken(
        userDetails: UserDetails
    ): String? {
        return buildToken(HashMap(), userDetails, refreshExpiration)
    }

    private fun buildToken(
        extraClaims: Map<String?, Any?>,
        userDetails: UserDetails,
        expiration: Long
    ): String? {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact()
    }

    fun isTokenValid(token: String?, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun extractAllClaims(token: String?): Claims {
        val parser = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
        return parser
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): SecretKey? {
        return Keys.hmacShaKeyFor(secretKey?.toByteArray())
    }
}