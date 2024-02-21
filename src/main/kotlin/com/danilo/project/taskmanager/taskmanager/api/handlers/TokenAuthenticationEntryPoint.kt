package com.danilo.project.taskmanager.taskmanager.api.handlers

import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.ErroResponse
import com.danilo.project.taskmanager.taskmanager.api.utils.FormatUtils
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Tratar de exceções ao tentar acessar rota sem autenticação, seja sem token ou token inválido
 */
@Component
class TokenAuthenticationEntryPoint : AuthenticationEntryPoint {

    /**
     * Para serealizar em JSON o nosso ObjetoResponse
     */
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?,
    ) {
        var status = HttpStatus.UNAUTHORIZED

        var errorResponse = ErroResponse(
            status = status.value(),
            timestamp = FormatUtils.dateFormatError(LocalDateTime.now()),
            mensagem = authException!!.localizedMessage,
            path = request!!.requestURI
        )

        var json = objectMapper.writeValueAsString(errorResponse)

        response?.apply {
            this.status = status.value()
            this.setHeader("Content-Type", "application/json")
            this.characterEncoding = "utf-8"
            this.writer.write(json)
        }


    }
}