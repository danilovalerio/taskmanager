package com.danilo.project.taskmanager.taskmanager.core.exceptions

import jakarta.persistence.EntityNotFoundException

class EntidadeNaoEncontradaException(msg: String) : EntityNotFoundException(msg) {
}