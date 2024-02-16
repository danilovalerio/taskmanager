package com.danilo.project.taskmanager.taskmanager.api.mappers

import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.UsuarioResponse
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import org.springframework.stereotype.Component

@Component
class ApiUsuarioMapper {

    fun toResponse(model: Usuario): UsuarioResponse {
        var usuarioResponse = UsuarioResponse().apply {
            this.id = model.id.toString()
            this.nomeSobreNome = "${model.nome} ${model.sobrenome}"
            this.email = model.email
            this.tipo = model.tipo?.name.toString()
            this.ativo = model.ativo
        }

        return usuarioResponse
    }
}