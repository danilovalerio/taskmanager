package com.danilo.project.taskmanager.taskmanager.api.controllers

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.TaskRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UpdatesTaskRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.TaskResponse
import com.danilo.project.taskmanager.taskmanager.api.services.ApiTaskService
import com.danilo.project.taskmanager.taskmanager.core.models.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskRestController {

    @Autowired
    private lateinit var service: ApiTaskService

    @GetMapping
    fun listTasks(): List<TaskResponse>{
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): TaskResponse {
        return  service.findById(id)
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun add(@RequestBody @Validated request: TaskRequest): TaskResponse {
        return service.add(request)
    }

    @PutMapping("/{id}")
    fun atualizarTask(
        @PathVariable id: Long,
        @RequestBody @Validated request: UpdatesTaskRequest
    ): TaskResponse {
        return service.update(id, request)
    }

    @PutMapping("/{id}/ativar")
    fun activate(
        @PathVariable id: Long): String {
        return service.activate(id)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): String {
        return service.deleteByCancel(id)
    }
}