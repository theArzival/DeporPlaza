package com.Sistema.DeporPlaza.config;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.Sistema.DeporPlaza.model.Rol;
import com.Sistema.DeporPlaza.model.Usuario;

public class UsuarioSecurity implements UserDetails {
    private Usuario usuario;

    public UsuarioSecurity(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return usuario.getNombres() + ", " + usuario.getApellidos();
    }

    public String getDniUsuario() {
        return usuario.getDni();
    }

    public String getNombresUsuario() {
        return usuario.getNombres();
    }

    public String getApellidosUsuario() {
        return usuario.getApellidos();
    }

    public String getEmailUsuario() {
        return usuario.getEmail();
    }

    public String getRolUsuario() {
        return usuario.getRol().getNombreRol();
    }

    public Integer getIdUsuario() {
        return usuario.getIdUsuario();
    }

    public String getTelefonoUsuario() {
        return usuario.getTelefono();
    }

    public String getEstadoUsuario() {
        return usuario.getEstado();
    }

    public LocalDate getFechaRegistroUsuario() {
        return usuario.getFechaRegistro();
    }

    public Rol getRol() {
        return usuario.getRol();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(
                "ROLE_" + usuario.getRol().getNombreRol());
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getEstado().equalsIgnoreCase("ACTIVO");
    }

}
