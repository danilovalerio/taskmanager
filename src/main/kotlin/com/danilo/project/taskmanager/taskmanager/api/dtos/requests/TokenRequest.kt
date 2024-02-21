package com.danilo.project.taskmanager.taskmanager.api.dtos.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TokenRequest (
    @field:NotNull
    @field:NotEmpty
    @field:Size(max = 255)
    @field:Email
    var email: String,

    @field:NotNull
    @field:NotEmpty
    @field:Size(max = 255)
    var senha: String
)