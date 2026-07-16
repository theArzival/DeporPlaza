package com.Sistema.DeporPlaza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.model.Horario;
import com.Sistema.DeporPlaza.model.Reserva;
import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.UsuarioRepository;
import com.Sistema.DeporPlaza.service.CampoService;
import com.Sistema.DeporPlaza.service.HorarioService;
import com.Sistema.DeporPlaza.service.ReservaService;
import com.Sistema.DeporPlaza.service.RolService;
import com.Sistema.DeporPlaza.service.TipoCampoService;
import com.Sistema.DeporPlaza.service.UsuarioService;

@Controller
public class LoginController {
    @Autowired
    HorarioService horarioService;
    @Autowired
    CampoService campoService;
    @Autowired
    TipoCampoService tipoCampoService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ReservaService reservaService;
    @Autowired
    RolService rolService;
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

    @GetMapping({ "/index", "/" })
    public String cargarIndex(Model modelo) {
        List<Horario> horarios = horarioService.listar();
        List<CampoDeportivo> campos = campoService.listar();
        modelo.addAttribute("reserva", new Reserva());
        modelo.addAttribute("horarios", horarios);
        modelo.addAttribute("campos", campos);
        return "index";
    }

}
