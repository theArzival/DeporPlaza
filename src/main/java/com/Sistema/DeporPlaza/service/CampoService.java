package com.Sistema.DeporPlaza.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.repository.CampoRepository;
import com.Sistema.DeporPlaza.repository.ReservaRepository;

@Service
public class CampoService {
    @Autowired
    private CampoRepository repo;
    @Autowired
    private ReservaRepository reservaRepository;
    private static final Logger log = LoggerFactory.getLogger(CampoService.class);

    public Long contar() {
        return repo.count();
    }

    public List<CampoDeportivo> listar() {
        return repo.findAll();
    }

    public void validarExistencia(Integer idCampo) {
        boolean campoExiste = reservaRepository.existsByCampoIdCampo(idCampo);

        if (campoExiste) {
            log.error("Se intento el eliminar un campo que tiene reservas registradas. Id del Campo: " + idCampo);
            throw new IllegalArgumentException(
                    "No se puede eliminar este campo. Se encuentra relacionado con una o mas reservas.");
        }
    }

    public void validar(CampoDeportivo campoDeportivo) {
        if (ObjectUtils.isEmpty(campoDeportivo)) {
            log.error("Se ha ingresado un Campo Deportivo nulo");
            throw new IllegalArgumentException("El campo deportivo no puede ser nulo");
        }

        // if (!ObjectUtils.isEmpty(campoDeportivo.getIdCampo())) {
        // log.warn("No se ha ingresado un ID para el campo ");
        // throw new IllegalArgumentException("No se asigno un ID al campo deportivo");
        // }
        if (StringUtils.isBlank(campoDeportivo.getNombreCampo())) {
            log.warn("No se ha ingresado el nombre para el campo deportivo ");
            throw new IllegalArgumentException("Debe ingresar el nombre del campo deportivo");
        }
        if ((campoDeportivo.getPrecioHora().compareTo(BigDecimal.ZERO) <= 0) ||
                ObjectUtils.isEmpty(campoDeportivo.getPrecioHora())) {
            log.warn("No se ha ingresado el nombre para el campo deportivo ");
            throw new IllegalArgumentException("Debe ingresar el nombre del campo deportivo");
        }
        if (ObjectUtils.isEmpty(campoDeportivo.getTipoCampo())) {
            log.warn("El Tipo de Campo esta vacio o nulo");
            throw new IllegalArgumentException("Debe seleccionar un tipo de campo");
        }
    }

    public void guardar(CampoDeportivo campo) {
        repo.save(campo);
        log.info(
                "Campo registrado correctamente. ID: {}, Nombre Campo: {}, Precio por Hora: {}",
                campo.getIdCampo(),
                campo.getNombreCampo(),
                campo.getPrecioHora());
    }

    public void eliminar(Integer idCampo) {
        repo.deleteById(idCampo);
    }

    public CampoDeportivo buscarById(Integer idCampo) {
        return repo.findById(idCampo).orElse(null);
    }

    public CampoDeportivo buscarByNombreRol(String nombreCampo) {
        return repo.findByNombreCampo(nombreCampo);
    }

    // public Integer ultimoId() {
    // Integer ultimoId = repo.findMaxId();
    // if (ultimoId == null) {
    // return 1;
    // }
    // return ultimoId + 1;
    // }
}
