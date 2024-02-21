package com.danilo.project.taskmanager.taskmanager.security

import com.danilo.project.taskmanager.taskmanager.api.handlers.TokenAccessDeniedHandler
import com.danilo.project.taskmanager.taskmanager.api.handlers.TokenAuthenticationEntryPoint
import com.danilo.project.taskmanager.taskmanager.core.enums.TipoUsuario
import com.danilo.project.taskmanager.taskmanager.core.filters.AccessTokenRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class Config {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    /**
     * Filter criado para autorização via token para nossa api
     */
    @Autowired
    private lateinit var accessTokenRequestFilter: AccessTokenRequestFilter

    @Autowired
    private lateinit var tokenAuthenticationEntryPoint: TokenAuthenticationEntryPoint

    @Autowired
    private lateinit var tokenAccessDeniedHandler: TokenAccessDeniedHandler

    @Autowired
    @Throws(java.lang.Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration?): AuthenticationManager {
        return authenticationConfiguration!!.getAuthenticationManager()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.authorizeHttpRequests {auth ->
            auth.requestMatchers(AntPathRequestMatcher("/api/auth/**")).permitAll()
            auth.requestMatchers(AntPathRequestMatcher("/api/usuarios")).hasAuthority(
                TipoUsuario.ADMINISTRADOR.name
            )
            //auth.anyRequest().authenticated()
            //auth.anyRequest().permitAll()
        }


        /**
         * Configuração de API
         */
        httpSecurity.csrf()
            .ignoringRequestMatchers("/api/**")
            .ignoringRequestMatchers("/auth/**")

        /**
         * accessTokenRequestFilter virá antes do filtro de autenticação padrão do Spring (UsernamePasswordAuthenticationFilter)
         */
        httpSecurity
            .addFilterBefore(accessTokenRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

        /**
         * authenticationEntryPoint: Trata exceções de acessar Rota sem estar autenticado (sem token ou token inválido)
         * accessDeniedHandler: Quando o usuário não tem acesso a rota
         */
        httpSecurity.exceptionHandling { exceptionHandlingCustomizer ->
            exceptionHandlingCustomizer
                .authenticationEntryPoint(tokenAuthenticationEntryPoint)
                .accessDeniedHandler(tokenAccessDeniedHandler)
        }

        return httpSecurity.build()
    }
}