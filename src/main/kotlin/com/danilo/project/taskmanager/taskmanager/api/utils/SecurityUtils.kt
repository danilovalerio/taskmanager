package com.danilo.project.taskmanager.taskmanager.api.utils

import com.danilo.project.taskmanager.taskmanager.auth.UsuarioAutenticado
import com.danilo.project.taskmanager.taskmanager.core.exceptions.CustomException
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityUtils {

    fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

    fun getUsuarioAutenticado(): Usuario {
        try {
            val tipoUsuarioLogado = (SecurityContextHolder.getContext().authentication.principal as UsuarioAutenticado)
            val usuario = tipoUsuarioLogado.getUsuario()
            return usuario
        } catch (e: Exception) {
            throw CustomException(e.localizedMessage)
        }

    }
}