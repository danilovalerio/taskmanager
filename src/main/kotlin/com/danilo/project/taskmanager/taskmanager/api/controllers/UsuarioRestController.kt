package com.danilo.project.taskmanager.taskmanager.api.controllers

import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.UsuarioResponse
import com.danilo.project.taskmanager.taskmanager.api.services.ApiUsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/usuarios")
class UsuarioRestController {

    @Autowired
    private lateinit var service: ApiUsuarioService

    @GetMapping
    fun listUsers(): List<UsuarioResponse>{
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UsuarioResponse {
        return  service.findById(id)
    }
}