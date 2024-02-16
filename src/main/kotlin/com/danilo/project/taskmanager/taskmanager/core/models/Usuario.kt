package com.danilo.project.taskmanager.taskmanager.core.models

import com.danilo.project.taskmanager.taskmanager.core.enums.TipoUsuario
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @Column(nullable = false)
    var nome: String? = null,

    @Column(nullable = false)
    var sobrenome: String? = null,

    @Column(nullable = false)
    var email: String? = null,

    @Column(nullable = false)
    var senha: String? = null,

    @Column(name = "tipo_usuario", length = 15, nullable = true)
    @Enumerated(EnumType.STRING)
    var tipo: TipoUsuario? = TipoUsuario.USUARIO,

    @Column(nullable = true, unique = true, length = 14)
    var cpfCnpj: String? = null,

    @Column(nullable = true)
    var nascimento: LocalDate? = null,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "telefone_id", nullable = true)
    var telefone: Telefone? = null,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "foto_documento", nullable = true)
    var foto: Foto? = null,

    @OneToOne(orphanRemoval = true, cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = true)
    var endereco: Endereco? = null,

    @Column(nullable = true)
    var ativo: Boolean? = true,
)
