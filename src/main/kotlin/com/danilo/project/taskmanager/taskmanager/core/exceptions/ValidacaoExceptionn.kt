package com.danilo.project.taskmanager.taskmanager.core.exceptions

import org.springframework.validation.FieldError

open class ValidacaoException(msg: String, campoErro: FieldError) : RuntimeException(msg) {

    private var fieldError = campoErro

    init {
        this.fieldError = campoErro
    }

    fun getFieldError(): FieldError {
        return fieldError
    }

}