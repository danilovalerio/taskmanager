package com.danilo.project.taskmanager.taskmanager.api.dtos.responses

import com.danilo.project.taskmanager.taskmanager.core.enums.Priority
import com.danilo.project.taskmanager.taskmanager.core.enums.Status
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TaskResponse (
    var id: Long? = 0,
    var title: String? = null,
    var description: String? = null,
    var priority: Priority? = Priority.LOW,
    var comment: String? = null,
    var status: Status? = Status.TODO,
    var ativa: Boolean? = true,
    var usuario: Usuario? = null
)
