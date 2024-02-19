package com.danilo.project.taskmanager.taskmanager.core.models

import com.danilo.project.taskmanager.taskmanager.core.enums.Priority
import com.danilo.project.taskmanager.taskmanager.core.enums.Status
import jakarta.persistence.*

@Entity
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,

    @Column(nullable = false, length = 32)
    var title: String? = null,

    @Column(nullable = true)
    var description: String? = null,

    @Column(name = "priority_task", nullable = true)
    @Enumerated(EnumType.STRING)
    var priority: Priority? = Priority.LOW,

    @Column(nullable = true)
    var comment: String? = null,

    @Column(name = "status_task", nullable = true)
    @Enumerated(EnumType.STRING)
    var status: Status? = Status.TODO,

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    var usuario: Usuario? = null,

    @Column(nullable = true)
    var ativa: Boolean? = true
)
