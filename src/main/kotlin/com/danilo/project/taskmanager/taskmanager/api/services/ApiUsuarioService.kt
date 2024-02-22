package com.danilo.project.taskmanager.taskmanager.api.services

import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UpdatesUsuarioRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.requests.UsuarioRequest
import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.UsuarioResponse
import com.danilo.project.taskmanager.taskmanager.api.mappers.ApiUsuarioMapper
import com.danilo.project.taskmanager.taskmanager.api.utils.SecurityUtils
import com.danilo.project.taskmanager.taskmanager.core.enums.TipoUsuario
import com.danilo.project.taskmanager.taskmanager.core.exceptions.CustomException
import com.danilo.project.taskmanager.taskmanager.core.exceptions.EntidadeNaoEncontradaException
import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import com.danilo.project.taskmanager.taskmanager.core.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError

@Service
class ApiUsuarioService {

    @Autowired
    private lateinit var repository: UsuarioRepository

    @Autowired
    private lateinit var usuarioMapper: ApiUsuarioMapper

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun add(request: UsuarioRequest): UsuarioResponse {
        var senhaCriptografada = passwordEncoder.encode(request.senha)

        var newUserAdd = usuarioMapper.toModel(request)
        newUserAdd.senha = senhaCriptografada

        try {
            val userAdded = repository.save(newUserAdd)
            return usuarioMapper.toResponse(userAdded)
        } catch (e: Exception) {
            throw (CustomException(
                "Não foi possível inserir usuário",
                FieldError("", Usuario::class.simpleName.toString(), e.message.toString())
            ))
        }
    }

    fun findById(id: Long): UsuarioResponse {
        val userFound = usuarioById(id)

        return usuarioMapper.toResponse(userFound)
    }

    fun usuarioById(id: Long): Usuario {
        val userLogado = SecurityUtils().getUsuarioAutenticado()

        if (userLogado.tipo?.name == TipoUsuario.ADMINISTRADOR.name) {
            return findUsuarioById(id)
        } else if (userLogado.id == id) {
            return findUsuarioById(id)
        } else {
            throw EntidadeNaoEncontradaException("Item não encontrado, entre em contato com o administrador")
        }
    }

    private fun findUsuarioById(id: Long): Usuario {
        return repository.findById(id).orElseThrow {
            (throw EntidadeNaoEncontradaException("Não foi possível encontrar esse id $id de usuário"))
        }
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

        if (userFound.ativo == false) {
            return "Exclusão já realizada com sucesso."
        }
        userFound.ativo = false
        repository.save(userFound)

        return "Exclusão realizada com sucesso."
    }

    private fun messageSituationActivate(usuarioResponse: UsuarioResponse): String {
        val msg = "O usuário ${usuarioResponse.nomeSobreNome} está ${
            if (usuarioResponse.ativo == true) "Ativo" else
                "Desativado"
        }"

        return msg
    }

}