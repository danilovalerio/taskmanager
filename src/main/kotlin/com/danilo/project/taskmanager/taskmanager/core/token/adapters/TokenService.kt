package com.danilo.project.taskmanager.taskmanager.core.token.adapters

interface TokenService {

    fun gerarTokenAccess(subject: String): String

    fun getSubjectDoAccessToken(accessToken: String): String

    fun gerarRefreshToken(subject: String): String

    fun getSubjectDoRefreshToken(refreshToken: String): String

}