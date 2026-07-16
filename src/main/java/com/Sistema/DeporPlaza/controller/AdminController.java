package com.Sistema.DeporPlaza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.model.Horario;
import com.Sistema.DeporPlaza.model.Reserva;
import com.Sistema.DeporPlaza.model.Rol;
import com.Sistema.DeporPlaza.model.TipoCampo;
import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.service.CampoService;
import com.Sistema.DeporPlaza.service.HorarioService;
import com.Sistema.DeporPlaza.service.ReservaService;
import com.Sistema.DeporPlaza.service.RolService;
import com.Sistema.DeporPlaza.service.TipoCampoService;
import com.Sistema.DeporPlaza.service.UsuarioService;

@Controller
public class AdminController {
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

    @GetMapping({ "/admin/dashboard", "/admin" })
    public String cargarAdminDashboard(Model modelo) {
        modelo.addAttribute("totalUsuarios",
                usuarioService.contar());

        modelo.addAttribute("totalCampos",
                campoService.contar());

        modelo.addAttribute("totalReservas",
                reservaService.contar());
        List<Reserva> ultimasReservas = reservaService.ultimas5Reservas();
        modelo.addAttribute("ultimasReservas", ultimasReservas);
        List<Object[]> reservasPorMes = reservaService.obtenerReservasPorMes();
        modelo.addAttribute("reservasPorMes", reservasPorMes);
        List<Object[]> reservasPorTipo = reservaService.obtenerReservasPorTipo();
        modelo.addAttribute("reservasPorTipo", reservasPorTipo);
        List<Object[]> usuariosPorMes = usuarioService.obtenerUsuariosPorMes();
        modelo.addAttribute("usuariosPorMes", usuariosPorMes);
        return "dashboard";
    }

    @GetMapping("/admin/reservas")
    public String cargarGestionarReservas(Model modelo) {
        List<Horario> horarios = horarioService.listar();
        List<CampoDeportivo> campos = campoService.listar();
        modelo.addAttribute("reserva", new Reserva());
        modelo.addAttribute("horarios", horarios);
        modelo.addAttribute("campos", campos);

        return "gestionarReservas";

    }

    @GetMapping("/admin/camposDeportivos")
    public String cargarCamposDeportivos(Model modelo) {
        List<CampoDeportivo> campos = campoService.listar();
        List<TipoCampo> tipos = tipoCampoService.listar();
        modelo.addAttribute("tipo", new TipoCampo());
        modelo.addAttribute("tipos", tipos);
        modelo.addAttribute("campo", new CampoDeportivo());
        modelo.addAttribute("campos", campos);
        return "gestionarCamposD";
    }

    @GetMapping("/admin/gestionarUsuarios")
    public String cargarGestionarUsuarios(Model modelo) {
        List<Usuario> usuarios = usuarioService.listar();
        List<Rol> roles = rolService.listar();
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("roles", roles);
        return "gestionarUsuarios";
    }

}
