package com.danilo.project.taskmanager.taskmanager.core.permissions

import org.springframework.security.access.prepost.PreAuthorize

annotation class TaskManagerPermissions {
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @Retention(AnnotationRetention.RUNTIME)
    @Target(
        AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
    )
    annotation class isAdministrador

}
