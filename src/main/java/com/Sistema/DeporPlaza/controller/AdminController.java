package com.Sistema.DeporPlaza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.model.Horario;
import com.Sistema.DeporPlaza.model.Reserva;
import com.Sistema.DeporPlaza.service.CampoService;
import com.Sistema.DeporPlaza.service.HorarioService;

@Controller
public class AdminController {
    @Autowired
    HorarioService horarioService;
    @Autowired
    CampoService campoService;

    @GetMapping("/admin/dashboard")
    public String cargarAdminDashboard(Model modelo) {
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
}
