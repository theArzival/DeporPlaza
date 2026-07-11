package com.Sistema.DeporPlaza.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.model.TipoCampo;
import com.Sistema.DeporPlaza.repository.CampoRepository;
import com.Sistema.DeporPlaza.service.ApachePoiService;
import com.Sistema.DeporPlaza.service.CampoService;
import com.Sistema.DeporPlaza.service.TipoCampoService;
import com.Sistema.DeporPlaza.specification.campoSpecification;

@RestController
public class CampoDeportivoRestController {
    @Autowired
    private CampoService campoService;
    @Autowired
    private CampoRepository campoRepository;
    @Autowired
    TipoCampoService tipoCampoService;
    @Autowired
    private ApachePoiService apachePoiService;

    // @GetMapping("/reserva/eventos")
    // // El RESQUESTPARAM es lo que va despues de ../eventos?(REQUESTPARAM) va como
    // // idAlgo=algo
    // public List<EventoCalendarDTO> eventos(@RequestParam Integer idCampo) {

    // return reservaService.obtenerEventosPorCampo(idCampo);

    // }

    @PostMapping("/admin/registrarCampo")
    public Map<String, Object> reservar(@ModelAttribute CampoDeportivo campo, @RequestParam Integer idTipoC) {
        Map<String, Object> response = new HashMap<>();

        try {
            TipoCampo tipo = new TipoCampo();
            tipo.setIdTipo(idTipoC);
            campo.setTipoCampo(tipo);
            campoService.validar(campo);
            campoService.guardar(campo);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("message", "Operación Exitosa!");
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al registrar: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/admin/eliminarCampoPorId")
    public Map<String, Object> eliminarCampo(@RequestParam Integer idCampo) {
        Map<String, Object> response = new HashMap<>();
        try {
            campoService.validarExistencia(idCampo);
            campoService.eliminar(idCampo);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("message", "Campo eliminado correctamente");
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al eliminar: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/obtenerCampoPorId")
    public Map<String, Object> cargarEditarCampo(@RequestParam Integer idCampo) {
        Map<String, Object> response = new HashMap<>();

        try {

            CampoDeportivo campoEditar = campoService.buscarById(idCampo);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("campo", campoEditar);
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al cargar el Campo: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/busquedaCampo")
    public Map<String, Object> busqueda(
            @RequestParam(required = false) String nombreCampo,
            @RequestParam(required = false) BigDecimal precioHoraMin,
            @RequestParam(required = false) BigDecimal precioHoraMax,
            @RequestParam(required = false) String tipoCampo,
            @RequestParam(required = false) String estado) {
        Map<String, Object> response = new HashMap<>();

        try {
            campoSpecification specification = new campoSpecification(nombreCampo, precioHoraMin, precioHoraMax,
                    tipoCampo, estado);
            List<CampoDeportivo> camposBusqueda = campoRepository.findAll(specification);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("campos", camposBusqueda);
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al cargar el Campo: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/obtenerTipos")
    public Map<String, Object> obtenerTipos() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<TipoCampo> tipos = tipoCampoService.listar();
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("tipos", tipos);
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al cargar el Campo: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/admin/registrarTipo")
    public Map<String, Object> registrarTipo(@ModelAttribute TipoCampo tipo) {
        Map<String, Object> response = new HashMap<>();

        try {
            tipoCampoService.validar(tipo);
            tipoCampoService.guardar(tipo);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("message", "Operación Exitosa!");
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al registrar: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/admin/eliminarTipo")
    public Map<String, Object> eliminarTipo(@RequestParam Integer idTipo) {
        Map<String, Object> response = new HashMap<>();
        try {
            tipoCampoService.validarExistencia(idTipo);
            tipoCampoService.eliminar(idTipo);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("message", "Tipo de campo eliminado correctamente");
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al eliminar: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/obtenerTipoPorId")
    public Map<String, Object> cargarEditarTipo(@RequestParam Integer idTipo) {
        Map<String, Object> response = new HashMap<>();

        try {

            TipoCampo tipoEditar = tipoCampoService.buscarById(idTipo);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("tipo", tipoEditar);
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al cargar el Campo: " + e.getMessage());
        }
        return response;
    }
}