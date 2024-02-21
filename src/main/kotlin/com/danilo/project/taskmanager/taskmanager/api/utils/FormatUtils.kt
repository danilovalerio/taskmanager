package com.danilo.project.taskmanager.taskmanager.api.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FormatUtils {

    fun dateFormatError(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}