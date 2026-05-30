package com.Sistema.DeporPlaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.config.UsuarioSecurity;
import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repo.findByEmail(email);
        if (usuario == null)
            throw new UsernameNotFoundException("Usuario no encontrado");
        return new UsuarioSecurity(usuario);
    }

}
