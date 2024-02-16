package com.danilo.project.taskmanager.taskmanager.core.models

import jakarta.persistence.*

@Entity
data class Telefone(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var numero: String? = null,

    @Column(nullable = false)
    var whatsapp: String? = null,
)
