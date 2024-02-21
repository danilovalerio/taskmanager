package com.danilo.project.taskmanager.taskmanager.auth

import com.danilo.project.taskmanager.taskmanager.core.models.Usuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UsuarioAutenticado(user: Usuario) : UserDetails {

    private var user: Usuario = user
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return  AuthorityUtils.createAuthorityList(user.tipo.toString())
    }

    override fun getPassword(): String {
        return user.senha ?: ""
    }

    override fun getUsername(): String {
        return user.email ?: ""
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getUsuario(): Usuario {
        return user
    }
}