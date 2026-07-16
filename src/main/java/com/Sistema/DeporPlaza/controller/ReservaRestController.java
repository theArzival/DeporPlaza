package com.Sistema.DeporPlaza.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Sistema.DeporPlaza.dto.EventoCalendarDTO;
import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.model.Horario;
import com.Sistema.DeporPlaza.model.Reserva;
import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.service.ApachePoiService;
import com.Sistema.DeporPlaza.service.ReservaService;
import com.Sistema.DeporPlaza.service.UsuarioService;

@RestController
public class ReservaRestController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ApachePoiService apachePoiService;

    @GetMapping("/reserva/eventos")
    // El RESQUESTPARAM es lo que va despues de ../eventos?(REQUESTPARAM) va como
    // idAlgo=algo
    public List<EventoCalendarDTO> eventos(@RequestParam Integer idCampo) {

        return reservaService.obtenerEventosPorCampo(idCampo);

    }

    @PostMapping("/reserva/registrar")
    public Map<String, Object> reservar(@ModelAttribute Reserva reserva,
            @RequestParam(required = false) Integer idCampo,
            @RequestParam(required = false) Integer idHorario,
            @RequestParam(required = false) String dniCliente) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (idCampo == null) {
                throw new IllegalArgumentException("Campo inválido.");
            }

            if (idHorario == null) {
                throw new IllegalArgumentException("Seleccione un el Horario a reservar.");
            }

            if (dniCliente == null || dniCliente.isBlank()) {
                throw new IllegalArgumentException("DNI inválido.");
            }
            Horario horario = new Horario();
            horario.setIdHorario(idHorario);
            CampoDeportivo campo = new CampoDeportivo();
            campo.setIdCampo(idCampo);
            Usuario usuario = usuarioService.buscarByDni(dniCliente);

            reserva.setUsuario(usuario);
            reserva.setCampo(campo);
            reserva.setHorario(horario);
            reservaService.validar(reserva);
            reservaService.guardar(reserva);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("message", "Reserva registrada correctamente!");
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al reservar: " + e.getMessage());
        } catch (Exception e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al reservar: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/reporteExcel")
    public ResponseEntity<byte[]> reporteExcel() throws IOException {
        List<Reserva> reservas = reservaService.listar();

        byte[] excel = apachePoiService
                .generarExcel(reservas);

        HttpHeaders headers = new HttpHeaders();

        headers.add(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=reservas.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM)
                .body(excel);
    }

}