package com.danilo.project.taskmanager.taskmanager.core.repositories

import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByEmail(email: String): Optional<Usuario>
}