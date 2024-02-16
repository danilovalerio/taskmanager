package com.danilo.project.taskmanager.taskmanager.core.models

import Endereco
import Foto
import Telefone
import TipoUsuario
import java.time.LocalDate

data class Usuario(
    val id: Long? = 0,

    var nome: String? = null,

    var sobrenome: String? = null,

    var email: String? = null,

    var senha: String? = null,

    var tipo: TipoUsuario? = null,

    var cpfCnpj: String? = null,

    var nascimento: LocalDate? = null,

    var telefone: Telefone? = null,

    var foto: Foto? = null,

    var endereco: Endereco? = null,

    var ativo: Boolean? = true,
)
