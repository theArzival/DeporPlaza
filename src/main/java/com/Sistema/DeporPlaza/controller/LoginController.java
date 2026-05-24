package com.Sistema.DeporPlaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.UsuarioRepository;
import com.Sistema.DeporPlaza.service.UsuarioService;

@Controller
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String cargarLogin(Model modelo) {
        modelo.addAttribute("usuarioRegister", new Usuario());
        modelo.addAttribute("usuarioLogin", new Usuario());
        return "login";
    }

}
