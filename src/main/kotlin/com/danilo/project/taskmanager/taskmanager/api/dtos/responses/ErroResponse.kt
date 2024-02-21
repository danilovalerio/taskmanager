package com.danilo.project.taskmanager.taskmanager.api.dtos.responses

data class ErroResponse(
    var status: Int,
    var timestamp: String,
    var mensagem: String,
    var path: String
)
