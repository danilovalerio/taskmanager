package com.danilo.project.taskmanager.taskmanager.core.models

import jakarta.persistence.*

@Entity
data class Foto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    val filename: String? = null,

    @Column(name = "content_length", nullable = false)
    val contentLength: Long? = null,

    @Column(name = "content_type", nullable = false)
    val contentType: String? = null,

    @Column(nullable = false)
    val url: String? = null,
)
