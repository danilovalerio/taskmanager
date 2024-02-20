package com.danilo.project.taskmanager.taskmanager.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import kotlin.jvm.Throws

@Configuration
@EnableWebMvc
class Config {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.authorizeHttpRequests {auth ->
            auth.anyRequest().permitAll()
        }


        /**
         * Configuração de API
         */
        httpSecurity.csrf()
            .ignoringRequestMatchers("/api/**")
            .ignoringRequestMatchers("/auth/**")

        return httpSecurity.build()
    }
}