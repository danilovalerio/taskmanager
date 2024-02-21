package com.danilo.project.taskmanager.taskmanager.auth

import com.danilo.project.taskmanager.taskmanager.core.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AutenticacaoService : UserDetailsService {

    @Autowired
    private lateinit var repository: UsuarioRepository

    override fun loadUserByUsername(email: String?): UserDetails {
        val msg = "Erro ao tentar conectar com usuário $email, entre em contato com o responsável."

        return repository.findByEmail(email ?: "").map { user ->
            UsuarioAutenticado(user)
        }.orElseThrow {
            //TODO: Tratar com Exception Personalizada
            RuntimeException(msg)
        }
    }
}