package com.danilo.project.taskmanager.taskmanager.api.services

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.TaskRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UpdatesTaskRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.TaskResponse
import com.danilo.project.taskmanager.taskmanager.api.mappers.ApiTaskMapper
import com.danilo.project.taskmanager.taskmanager.core.enums.Status
import com.danilo.project.taskmanager.taskmanager.core.models.Task
import com.danilo.project.taskmanager.taskmanager.core.repositories.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApiTaskService {

    @Autowired
    private lateinit var repository: TaskRepository

    @Autowired
    private lateinit var taskMapper: ApiTaskMapper

    fun add(request: TaskRequest): Task {
        val newTaskAdd = taskMapper.toModel(request)

        val newTaskAdded = repository.save(newTaskAdd)

        return newTaskAdded
    }

    fun findById(id: Long): TaskResponse {
        val taskFound = taskById(id)

        return taskMapper.toResponse(taskFound)
    }

    fun taskById(id: Long): Task {
        val taskFound = repository.findById(id).orElseThrow {
            (throw RuntimeException("Não foi possível encontrar esse id $id de task."))
        }

        return taskFound
    }

    fun findAll(): List<TaskResponse> {
        val tasks: List<TaskResponse> = repository.findAll().map { task ->
            taskMapper.toResponse(task)
        }
        return tasks

    }

    fun update(idTask: Long, updatesTaskRequest: UpdatesTaskRequest): TaskResponse {

        var taskFound = taskById(idTask)

        /*taskFound.apply {
            this.title = updatesTaskRequest.title
            this.description = updatesTaskRequest.description
            this.priority = taskMapper.intToPriority(
                updatesTaskRequest.priority ?: 0)
            this.status = taskMapper.intToStatus(updatesTaskRequest.status ?: 0)
            this.comment = updatesTaskRequest.comment
            this.usuario = Mock.userMocked()
        }*/

        taskFound = taskMapper.toModel(idTask, updatesTaskRequest)



//        taskFound = taskMapper.toModel(updatesTaskRequest)
//        taskFound.id = idTask

        return taskMapper.toResponse(repository.save(taskFound))

    }

    fun activate(id: Long): String {

        val taskFound = taskById(id)
        var response: TaskResponse?

        if (taskFound.ativa == false) {
            taskFound.ativa = true
            response = taskMapper.toResponse(repository.save(taskFound))
        } else {
            response = taskMapper.toResponse(taskFound)
        }

        return messageSituationActivate(response)

    }

    fun deleteByCancel(id: Long): String {
        var taskFound = taskById(id)
        taskFound.status = Status.CANCELED
        repository.save(taskFound)

        return "Task excluída com sucesso."
    }

    private fun messageSituationActivate(taskResponse: TaskResponse): String {
        val msg = "A task ${taskResponse.title} está ${
            if (taskResponse.ativa == true) "Ativada" else
                "Desativada"
        }"

        return msg
    }

}