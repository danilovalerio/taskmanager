package com.danilo.project.taskmanager.taskmanager.core.models

import jakarta.persistence.*

@Entity
data class Endereco (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 8)
    var cep: String? = null,

    @Column(nullable = false, length = 120)
    var lograudouro: String? = null,

    @Column(nullable = false, length = 15)
    var numero: Int? = null,

    @Column(nullable = true)
    var complemento: String? = null,

    @Column(nullable = false, length = 30)
    var bairro: String? = null,

    @Column(nullable = false, length = 30)
    var cidade: String? = null,

    @Column(nullable = false, length = 2)
    var estado: String? = null
)
