package com.danilo.project.taskmanager.taskmanager.api.controllers

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.RefreshRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.TokenRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.TokenResponse
import com.danilo.project.taskmanager.taskmanager.api.services.ApiAuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthRestController {

    @Autowired
    private lateinit var service: ApiAuthService

    @PostMapping("/token")
    fun autenticar(@RequestBody @Valid tokenRequest: TokenRequest): TokenResponse {
        return service.autenticar(tokenRequest)
    }

    @PostMapping("/refresh")
    fun reAutenticar(@RequestBody @Valid refreshRequest: RefreshRequest): TokenResponse {
        return service.reAutenticar(refreshRequest)
    }

    @PostMapping("/logout")
    fun logout(@RequestBody @Valid refreshRequest: RefreshRequest): ResponseEntity<Void> {
        service.logout(refreshRequest)
        /**
         * reset_content
         * Informa que teve um reset do conteúdo enviado
         */
        return ResponseEntity(HttpStatus.RESET_CONTENT)
    }

}