package com.danilo.project.taskmanager.taskmanager.api.dtos.requests

import com.danilo.project.taskmanager.taskmanager.core.enums.TipoUsuario
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

data class UsuarioRequest(

    @field:Size(min = 3, max = 255)
    @field:NotNull
    @field:NotEmpty
    var nome: String? = null,

    @field:Size(min = 3, max = 255)
    @field:NotNull
    @field:NotEmpty
    var sobrenome: String? = null,

    @field:Size(min = 3, max = 255)
    @field:NotNull
    @field:NotEmpty
    @field:Email
    var email: String? = null,

    @field:Size(min = 8, max = 18)
    @field:NotNull
    @field:NotEmpty
    var senha: String? = null,

    @field:Size(min = 11, max = 14)
    @field:NotNull
    @field:NotEmpty
    var cpfCnpj: String? = null,

    var ativo: Boolean? = true,

    var tipo: TipoUsuario? = TipoUsuario.USUARIO,

    )