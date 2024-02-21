package com.danilo.project.taskmanager.taskmanager.api.services

import com.danilo.project.taskmanager.taskmanager.core.exceptions.TokenNaBlackListException
import com.danilo.project.taskmanager.taskmanager.core.models.TokenBlackList
import com.danilo.project.taskmanager.taskmanager.core.repositories.TokenBlackListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TokenBlackListService {

    @Autowired
    private lateinit var repository: TokenBlackListRepository

    fun verificarToken(token: String) {
        var token = repository.findByToken(token).isPresent

        if (token) {
            throw TokenNaBlackListException("Token informado está indisponível.")
        }
    }

    fun adicionarNaBlackList(token: String) {
        if (!repository.existsByToken(token)) {
            repository.save(TokenBlackList(null, token))
        }
    }

}