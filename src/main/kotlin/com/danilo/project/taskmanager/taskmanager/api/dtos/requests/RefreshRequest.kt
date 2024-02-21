package com.danilo.project.taskmanager.taskmanager.api.dtos.requests

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class RefreshRequest(
    @field:NotNull
    @field:NotEmpty
    var refresh: String
)
