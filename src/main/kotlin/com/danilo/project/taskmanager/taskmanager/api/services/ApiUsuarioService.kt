package com.danilo.project.taskmanager.taskmanager.api.services

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UpdatesUsuarioRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UsuarioRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.UsuarioResponse
import com.danilo.project.taskmanager.taskmanager.api.mappers.ApiUsuarioMapper
import com.danilo.project.taskmanager.taskmanager.core.exceptions.EntidadeNaoEncontradaException
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import com.danilo.project.taskmanager.taskmanager.core.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ApiUsuarioService {

    @Autowired
    private lateinit var repository: UsuarioRepository

    @Autowired
    private lateinit var usuarioMapper: ApiUsuarioMapper

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun add(request: UsuarioRequest): Usuario {
        var senhaCriptografada = passwordEncoder.encode(request.senha)

        //TODO: usar o toModel do Mapper
        var newUserAdd = Usuario().apply {
            this.nome = request.nome
            this.sobrenome = request.sobrenome
            this.email = request.email
            this.senha = senhaCriptografada
            this.cpfCnpj = request.cpfCnpj
            this.ativo = request.ativo
            this.tipo = request.tipo
        }

        val userAdded = repository.save(newUserAdd)

        return userAdded
    }

    fun findById(id: Long): UsuarioResponse {
        val userFound = usuarioById(id)

        return usuarioMapper.toResponse(userFound)
    }

    fun usuarioById(id: Long): Usuario {
        val userFound = repository.findById(id).orElseThrow {
            (throw EntidadeNaoEncontradaException("Não foi possível encontrar esse id $id de usuário"))
        }

        return userFound
    }

    fun findAll(): List<UsuarioResponse> {
        val users: List<UsuarioResponse> = repository.findAll().map { user ->
            usuarioMapper.toResponse(user)
        }
        return users

    }

    fun update(idUsuario: Long, updatesUsuarioRequest: UpdatesUsuarioRequest): UsuarioResponse {

        val userFound = usuarioById(idUsuario)

        userFound.nome = updatesUsuarioRequest.nome
        userFound.sobrenome = updatesUsuarioRequest.sobrenome

        return usuarioMapper.toResponse(repository.save(userFound))

    }

    fun activate(idUsuario: Long): String {

        val userFound = usuarioById(idUsuario)
        var response: UsuarioResponse? = null

        if (userFound.ativo == false) {
            userFound.ativo = true
            response = usuarioMapper.toResponse(repository.save(userFound))
        } else {
            response = usuarioMapper.toResponse(userFound)
        }

        return messageSituationActivate(response)

    }

    fun deleteByInative(id: Long): String {
        var userFound = usuarioById(id)
        userFound.ativo = false
        repository.save(userFound)

        return "Usuário excluído com sucesso."
    }

    private fun messageSituationActivate(usuarioResponse: UsuarioResponse): String{
       val msg = "O usuário ${usuarioResponse.nomeSobreNome} está ${if (usuarioResponse.ativo == true) "Ativo" else
           "Desativado"}"

        return msg
    }

}