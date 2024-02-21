package com.danilo.project.taskmanager.taskmanager.api.mappers

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.TaskRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UpdatesTaskRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.TaskResponse
import com.danilo.project.taskmanager.taskmanager.api.utils.SecurityUtils
import com.danilo.project.taskmanager.taskmanager.core.enums.Priority
import com.danilo.project.taskmanager.taskmanager.core.enums.Status
import com.danilo.project.taskmanager.taskmanager.core.models.Task
import org.springframework.stereotype.Component

@Component
class ApiTaskMapper {

    fun toResponse(model: Task): TaskResponse {
        val taskResponse = TaskResponse().apply {
            this.id = model.id
            this.title = model.title
            this.description = model.description
            this.priority = model.priority
            this.comment = model.comment
            this.status = model.status
            this.ativa = model.ativa
            //this.usuario = model.usuario
        }

        return taskResponse
    }

    fun toModel(request: TaskRequest): Task {

        val taskModel = Task().apply {
            this.title = request.title
            this.description = request.description
            this.priority = intToPriority(request.priority ?: 0)
            this.comment = request.comment
            this.usuario = SecurityUtils().getUsuarioAutenticado()
        }

        return taskModel
    }

    fun toModel(id: Long, request: UpdatesTaskRequest): Task {

        val taskModel = Task().apply {
            this.id = id
            this.title = request.title
            this.description = request.description
            this.priority = intToPriority(request.priority ?: 0)
            this.status = intToStatus(request.status ?: 0)
            this.comment = request.comment
            this.usuario = SecurityUtils().getUsuarioAutenticado()
        }

        return taskModel
    }

    fun intToPriority(value: Int): Priority {

        return when (value) {
            Priority.HIGH.ordinal -> Priority.HIGH
            Priority.MEDIUM.ordinal -> Priority.MEDIUM
            Priority.LOW.ordinal -> Priority.LOW
            else -> Priority.LOW
        }

    }

    fun intToStatus(value: Int): Status {

        return when (value) {
            Status.TODO.ordinal -> Status.TODO
            Status.IN_PROGRESS.ordinal -> Status.IN_PROGRESS
            Status.DONE.ordinal -> Status.DONE
            Status.CANCELED.ordinal -> Status.CANCELED
            else -> Status.TODO
        }

    }
}
