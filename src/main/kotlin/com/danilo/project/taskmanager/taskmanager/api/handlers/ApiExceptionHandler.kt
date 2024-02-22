package com.danilo.project.taskmanager.taskmanager.api.handlers

import com.danilo.project.taskmanager.taskmanager.api.dtos.responses.ErroResponse
import com.danilo.project.taskmanager.taskmanager.api.utils.FormatUtils
import com.danilo.project.taskmanager.taskmanager.core.exceptions.CustomException
import com.danilo.project.taskmanager.taskmanager.core.exceptions.ValidacaoException
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@RestControllerAdvice(annotations = [RestController::class])
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    private var camelCaseToSnakeCase = PropertyNamingStrategies.SnakeCaseStrategy()

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException, request: HttpServletRequest): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST

        val errorResponse = ErroResponse(
            status = status.value(),
            timestamp = FormatUtils.dateFormatError(LocalDateTime.now()),
            mensagem = "${exception.localizedMessage} /n ${exception.getFieldError().defaultMessage?.take(255) ?: ""}",
            path = request.requestURI
        )

        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(ValidacaoException::class)
    fun handleValidacaoException(exception: ValidacaoException): ResponseEntity<Any>? {
        var body: HashMap<String, List<String>> = hashMapOf()
        var fieldError = exception.getFieldError()
        var fieldErrors: ArrayList<String> = arrayListOf()

        fieldErrors.add(fieldError.defaultMessage ?: "")
        var field = camelCaseToSnakeCase.translate(fieldError.field)
        body.put(field, fieldErrors)

        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(
        exception: EntityNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<Any>? {
        val status = HttpStatus.NOT_FOUND

        val errorResponse = ErroResponse(
            status = status.value(),
            timestamp = FormatUtils.dateFormatError(LocalDateTime.now()),
            mensagem = exception.localizedMessage,
            path = request.requestURI
        )

        return ResponseEntity(errorResponse, status)
    }
}