package com.Sistema.DeporPlaza.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sistema.DeporPlaza.model.Rol;
import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.RolRepository;
import com.Sistema.DeporPlaza.service.UsuarioService;

@RestController
public class RegisterRestController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public Map<String, String> registrar(@ModelAttribute Usuario usuario) {
        Map<String, String> resultado = new HashMap<>();

        try {
            // Rol rol = rolRepository.findById(2).orElseThrow();
            Rol rol = rolRepository.findByNombreRol("USER");
            usuario.setRol(rol);
            usuarioService.validar(usuario);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioService.guardar(usuario);
            resultado.put("success", "Usuario registrado exitosamente");

        } catch (IllegalArgumentException e) {
            resultado.put("error", "Error al registrar el usuario: " + e.getMessage());
        }

        return resultado;
    }

}
