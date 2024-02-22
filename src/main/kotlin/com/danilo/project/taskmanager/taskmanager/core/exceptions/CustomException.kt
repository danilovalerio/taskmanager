package com.danilo.project.taskmanager.taskmanager.core.exceptions

import org.springframework.validation.FieldError

open class CustomException(msg: String, field: FieldError) : ValidacaoException(msg, field) {


}