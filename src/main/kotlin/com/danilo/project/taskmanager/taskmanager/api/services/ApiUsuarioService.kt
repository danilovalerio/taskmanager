package com.danilo.project.taskmanager.taskmanager.api.services

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UpdatesUsuarioRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UsuarioRequest
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import com.danilo.project.taskmanager.taskmanager.core.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ApiUsuarioService {

    @Autowired
    private lateinit var repository: UsuarioRepository

    fun add(request: UsuarioRequest): Usuario {
        var newUserAdd = Usuario().apply {
            this.nome = request.nome
            this.sobrenome = request.sobrenome
            this.email = request.email
            this.senha = request.senha
            this.cpfCnpj = request.cpfCnpj
            this.ativo = request.ativo
            this.tipo = request.tipo
        }

        val userAdded = repository.save(newUserAdd)

        return userAdded
    }

    fun findById(id: Long): Usuario {
        val userFound = repository.findById(id).orElseThrow {
            (throw RuntimeException("Não foi possível encontrar esse id $id de usuário"))
        }

        return userFound
    }

    fun update(idUsuario: Long, updatesUsuarioRequest: UpdatesUsuarioRequest): Usuario{

        var userFound = findById(idUsuario)

        userFound.nome = updatesUsuarioRequest.nome
        userFound.sobrenome = updatesUsuarioRequest.sobrenome

        return repository.save(userFound)

    }

    fun deleteByInative(id: Long): String {
            var userFound = findById(id)
            userFound.ativo = false
            repository.save(userFound)

            return "Usuário excluído com sucesso."
    }

}