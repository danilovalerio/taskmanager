package com.danilo.project.taskmanager.taskmanager.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    @Override
    fun addCorsMapping(registry: CorsRegistry) {
        println("CONFIG CORS FUNCIONADNO?")
        //TODO("TESTAR O FUNCIONAMENTO COM APLICACAO FRONT POSTERIORMENTE)
        registry.addMapping("/api/**")
            .allowedMethods("*") //Todos os métodos de GET, POST, DELETE etc
            .allowedOrigins("*") //Permite qualqer dominio ou ip de dispositivo mobile
    }
}