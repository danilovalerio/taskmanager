package com.danilo.project.taskmanager.taskmanager.core.repositories

import com.danilo.project.taskmanager.taskmanager.core.models.TokenBlackList
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TokenBlackListRepository : JpaRepository<TokenBlackList, Long> {

    fun findByToken(token: String) : Optional<TokenBlackList>

    fun existsByToken(token: String): Boolean

}