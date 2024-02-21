package com.danilo.project.taskmanager.taskmanager.api.dtos.responses

data class TokenResponse (
    var access: String,
    var refresh: String
)