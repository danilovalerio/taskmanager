package com.danilo.project.taskmanager.taskmanager.core.enums

enum class Status(id: Int, description: String) {
    TODO(0, "A fazer"),
    IN_PROGRESS(1, "Em andamento"),
    DONE(2, "Concluída"),
    CANCELED(3, "Cancelada"),
}
