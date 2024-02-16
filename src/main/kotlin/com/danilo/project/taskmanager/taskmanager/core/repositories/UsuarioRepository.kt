package com.danilo.project.taskmanager.taskmanager.core.repositories

import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Long, Usuario> {
}