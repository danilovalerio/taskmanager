package com.danilo.project.taskmanager.taskmanager.api.controllers

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UpdatesUsuarioRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UsuarioRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.UsuarioResponse
import com.danilo.project.taskmanager.taskmanager.api.services.ApiUsuarioService
import com.danilo.project.taskmanager.taskmanager.core.permissions.TaskManagerPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
class UsuarioRestController {

    @Autowired
    private lateinit var service: ApiUsuarioService

    @GetMapping
    @TaskManagerPermissions.isAdministrador
    fun listUsers(): List<UsuarioResponse>{
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UsuarioResponse {
        return  service.findById(id)
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun add(@RequestBody @Validated request: UsuarioRequest): UsuarioResponse {
        return service.add(request)
    }

    @PutMapping("/{id}")
    fun atualizarEmpresa(
        @PathVariable id: Long,
        @RequestBody @Validated request: UpdatesUsuarioRequest): UsuarioResponse {
        return service.update(id, request)
    }

    @TaskManagerPermissions.isAdministrador
    @PutMapping("/{id}/ativar")
    fun activate(
        @PathVariable id: Long): String {
        return service.activate(id)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): String {
        return service.deleteByInative(id)
    }
}