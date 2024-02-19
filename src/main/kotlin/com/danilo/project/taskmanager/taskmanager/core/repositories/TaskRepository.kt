package com.danilo.project.taskmanager.taskmanager.core.repositories

import com.danilo.project.taskmanager.taskmanager.core.models.Task
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TaskRepository : JpaRepository<Task, Long> {

    fun findByUsuario(usuario: Usuario): Optional <List<Task>>
}