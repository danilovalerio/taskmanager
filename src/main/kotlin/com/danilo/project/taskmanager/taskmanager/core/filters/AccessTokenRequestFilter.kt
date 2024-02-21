package com.danilo.project.taskmanager.taskmanager.core.filters

import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.ErroResponse
import com.danilo.project.taskmanager.taskmanager.core.token.adapters.TokenService
import com.danilo.project.taskmanager.taskmanager.core.token.exceptions.TokenServiceException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Filtro será executado 1 vez a cada requisição feita pela aplicação
 */

@Component
class AccessTokenRequestFilter : OncePerRequestFilter() {

    private val TOKEN_TYPE: String = "Bearer "

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    /**
     * Para serializar em JSON o nosso ObjetoResponse
     */
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    /**
     * Aqui ficará todo o contexto de autorização
     */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            tryDoFilterInternal(request, filterChain, response)
        } catch (e: TokenServiceException) {
            var status = HttpStatus.UNAUTHORIZED

            val df = DateTimeFormatter.ISO_LOCAL_DATE_TIME

            var errorResponse = ErroResponse(
                status = status.value(),
                timestamp = LocalDateTime.now().format(df),
                mensagem = e.localizedMessage,
                path = request.requestURI
            )

            var json = objectMapper.writeValueAsString(errorResponse)

            response.apply {
                this.status = status.value()
                this.setHeader("Content-Type", "application/json")
                this.characterEncoding = "utf-8"
                this.writer.write(json)
            }

            throw TokenServiceException(e.localizedMessage)
        }
    }

    private fun tryDoFilterInternal(
        request: HttpServletRequest,
        filterChain: FilterChain,
        response: HttpServletResponse,
    ) {
        var token = ""
        var email = ""

        /**
         * Pega o token dentro do Header
         */
        var authorizationHeader = request.getHeader("Authorization")

        if (isTokenPresente(authorizationHeader)) {
            token = authorizationHeader.substring(TOKEN_TYPE.length)
            email = tokenService.getSubjectDoAccessToken(token)
        }

        if (isEmailNaoEstaNoContexto(email)) {
            adicionarEmailNoContexto(request, email)
        }

        /**
         * Filter Chain é uma cadeia de filtro que é executada
         * Aqui informações do próximo filter para ser executado, passando request e response
         */
        filterChain.doFilter(request, response)
    }

    private fun isTokenPresente(authorizationHeader: String? = null): Boolean {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE)
    }

    private fun isEmailNaoEstaNoContexto(email: String): Boolean {
        return email.isNotEmpty() && SecurityContextHolder.getContext().authentication == null
    }

    private fun adicionarEmailNoContexto(request: HttpServletRequest, email: String) {
        /**
         * Usuario autenticado
         */
        var usuario = userDetailsService.loadUserByUsername(email)

        /**
         * Objeto de Autenticação
         * @param[usuario] Equivalente ao principal
         * @param[credentials] Será nulo, pois se passou o token ele já está autorizado
         * @param[authorities] tipo de permissão que ele tem
         */
        var autenticacao = UsernamePasswordAuthenticationToken(usuario, null, usuario.authorities)

        /**
         * WebAuthenticationDetailsSource
         * @param[request] vai prover informações da autenticação por exemplo:
         * IP que usuário usou entre outras verificar quais depois
         */
        autenticacao.details = WebAuthenticationDetailsSource().buildDetails(request)

        /**
         * Adiciona a autenticação dentro do contexto do Spring Security,
         * assim agora tem um usuário logado para verificar acessos entre outros
         */
        SecurityContextHolder.getContext().authentication = autenticacao
    }
}