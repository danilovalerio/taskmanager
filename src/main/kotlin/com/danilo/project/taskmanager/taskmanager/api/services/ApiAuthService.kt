package com.danilo.project.taskmanager.taskmanager.api.services

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.RefreshRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.TokenRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.TokenResponse
import com.danilo.project.taskmanager.taskmanager.core.token.adapters.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class ApiAuthService {

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenBlackListService: TokenBlackListService

    fun autenticar(token: TokenRequest): TokenResponse {
        var email = token.email
        var senha = token.senha

        var autenticacao = UsernamePasswordAuthenticationToken(email, senha)

        authenticationManager.authenticate(autenticacao)

        val access = tokenService.gerarTokenAccess(email)
        val refresh = tokenService.gerarRefreshToken(email)

        return TokenResponse(access, refresh)

    }

    fun reAutenticar(refreshRequest: RefreshRequest): TokenResponse {
        var token = refreshRequest.refresh
        tokenBlackListService.verificarToken(token)

        var email = tokenService.getSubjectDoRefreshToken(token)

        userDetailsService.loadUserByUsername(email)

        var access = tokenService.gerarTokenAccess(email)
        var refresh = tokenService.gerarRefreshToken(email)

        tokenBlackListService.adicionarNaBlackList(token)

        return TokenResponse(access, refresh)
    }

    fun logout(refreshRequest: RefreshRequest) {
        var token = refreshRequest.refresh
        tokenBlackListService.adicionarNaBlackList(token)
    }
}