package com.danilo.project.taskmanager.taskmanager.core.mock

import com.danilo.project.taskmanager.taskmanager.core.enums.TipoUsuario
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario

object Mock {

    fun userMocked() : Usuario {
        var useMock = Usuario().apply {
            this.id = 1L
            this.nome = "Mock"
            this.sobrenome = "da Silva"
            this.email = "mocksilva@email.com"
            this.senha = "12345678"
            this.cpfCnpj = "12345678901"
            this.ativo = true
            this.tipo = TipoUsuario.USUARIO
        }

        return useMock
    }

}