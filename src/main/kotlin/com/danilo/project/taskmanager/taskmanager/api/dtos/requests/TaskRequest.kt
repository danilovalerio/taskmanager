package com.danilo.project.taskmanager.taskmanager.api.dtos.requests

import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

data class TaskRequest(
    @field:Size(min = 3, max = 32)
    @field:NotNull
    @field:NotEmpty
    var title: String? = null,

    var description: String? = null,

    @field:NotNull
    var priority: Int? = 3,

    var comment: String? = null,

    var usuario: Usuario? = null,
)
