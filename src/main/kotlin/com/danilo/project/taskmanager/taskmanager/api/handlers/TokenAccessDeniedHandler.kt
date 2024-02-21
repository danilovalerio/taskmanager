package com.danilo.project.taskmanager.taskmanager.api.handlers

import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.ErroResponse
import com.danilo.project.taskmanager.taskmanager.api.utils.FormatUtils
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Tratar de exceções ao tentar acessar rota com autenticação, mas authority não permitida
 */
@Component
class TokenAccessDeniedHandler : AccessDeniedHandler {

    /**
     * Para serealizar em JSON o nosso ObjetoResponse
     */
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?,
    ) {
        var status = HttpStatus.FORBIDDEN

        var errorResponse = ErroResponse(
            status = status.value(),
            timestamp = FormatUtils.dateFormatError(LocalDateTime.now()),
            mensagem = accessDeniedException!!.localizedMessage,
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