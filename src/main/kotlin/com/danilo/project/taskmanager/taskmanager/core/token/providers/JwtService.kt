package com.danilo.project.taskmanager.taskmanager.core.token.providers

import com.danilo.project.taskmanager.taskmanager.core.token.adapters.TokenService
import com.danilo.project.taskmanager.taskmanager.core.token.exceptions.TokenServiceException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class JwtService : TokenService {

    @Value("\${token.accessKey}")
    private lateinit var accessKey: String

    @Value("\${token.secondsExpiration}")
    private lateinit var expirationTokenSeconds: Integer

    @Value("\${token.refreshKey}")
    private lateinit var refreshTokenSignKey: String

    @Value("\${token.refreshSecondsExpiration}")
    private lateinit var refreshExpirationTokenSeconds: Integer

    override fun gerarTokenAccess(subject: String): String {
        return gerarTokenJwt(accessKey, expirationTokenSeconds.toInt(), subject)
    }

    override fun getSubjectDoAccessToken(accessToken: String): String {
        var claims = getClaims(accessToken, accessKey)

        return claims.subject
    }

    override fun gerarRefreshToken(subject: String): String {
        return gerarTokenJwt(refreshTokenSignKey, refreshExpirationTokenSeconds.toInt(), subject)
    }

    override fun getSubjectDoRefreshToken(refreshToken: String): String {
        var claims = getClaims(refreshToken, refreshTokenSignKey)
        return claims.subject
    }

    private fun gerarTokenJwt(signKey: String, expiration: Int, subject: String): String {

        /**
         * @property claims são as informações contidadas no token
         */
        var claims = HashMap<String, Any>()

        var dataHoraAtual = Instant.now()
        var dataHoraExpiracao = dataHoraAtual.plusSeconds(expiration.toLong())

        /**
         * toEpockMilli converte o Instant para um Date em formato long
         */
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(dataHoraAtual.toEpochMilli()))
            .setExpiration(Date(dataHoraExpiracao.toEpochMilli()))
            .signWith(SignatureAlgorithm.HS512, signKey)
            .compact()

    }

    private fun getClaims(token: String, signKey: String): Claims {
        try {
            return tryGetClaims(signKey, token)!!
        } catch (e: JwtException){
            throw TokenServiceException(e.localizedMessage)
        }
    }

    private fun tryGetClaims(signKey: String, token: String): Claims? {
        return Jwts.parser()
            .setSigningKey(signKey)
            .parseClaimsJws(token)
            .body
    }
}