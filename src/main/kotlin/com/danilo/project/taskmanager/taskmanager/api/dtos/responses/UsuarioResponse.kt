package com.danilo.project.taskmanager.taskmanager.api.dtos.responses

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UsuarioResponse(
    var id: String? = "",
    var nomeSobreNome: String? = "",
    var email: String? = "",
    var tipo: String? = "",
    var ativo: Boolean? = null
)
