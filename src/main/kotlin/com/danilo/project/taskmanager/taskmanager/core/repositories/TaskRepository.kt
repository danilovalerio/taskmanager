package com.danilo.project.taskmanager.taskmanager.core.repositories

import com.danilo.project.taskmanager.taskmanager.core.models.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Long, Task> {
}