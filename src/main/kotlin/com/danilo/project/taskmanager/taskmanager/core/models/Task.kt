package com.danilo.project.taskmanager.taskmanager.core.models

import com.danilo.project.taskmanager.taskmanager.core.enums.Priority
import com.danilo.project.taskmanager.taskmanager.core.enums.Status


data class Task(
    val id: Long? = 0,
    var title: String? = null,
    var description: String? = null,
    var priority: Priority? = Priority.LOW,
    var comment: String? = null,
    var status: Status? = Status.TODO,
    var user: Usuario? = null,
    var ativa: Boolean? = true
)
