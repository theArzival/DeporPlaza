package com.Sistema.DeporPlaza.service;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.TipoCampo;
import com.Sistema.DeporPlaza.repository.CampoRepository;
import com.Sistema.DeporPlaza.repository.TipoCampoRespository;

@Service
public class TipoCampoService {
    @Autowired
    private TipoCampoRespository tipoCampoRespository;
    @Autowired
    private CampoRepository campoRepository;
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);

    public List<TipoCampo> listar() {
        return tipoCampoRespository.findAll();
    }

    public void validar(TipoCampo tipoCampo) {
        if (ObjectUtils.isEmpty(tipoCampo)) {
            log.error("Se ha ingresado un Tipo Campo nulo");
            throw new IllegalArgumentException("El tipo de campo no puede ser nula");
        }

        if (StringUtils.isEmpty(tipoCampo.getNombreTipo())) {
            log.warn("No se ingreso el nombre del tipo de campo deportivo ");
            throw new IllegalArgumentException("Debe ingresar el nombre del tipo de campo deportivo");
        }
    }

    public void guardar(TipoCampo tipoCampo) {

        tipoCampoRespository.save(tipoCampo);
        log.info(
                "Tipo de campo registrado correctamente. Id Tipo: {}, Nombre Tipo: {}",
                tipoCampo.getIdTipo(),
                tipoCampo.getNombreTipo());
    }

    public void validarExistencia(Integer idTipo) {
        boolean tipoExiste = campoRepository.existsByTipoCampoIdTipo(idTipo);

        if (tipoExiste) {
            log.error("Se intento el eliminar un campo que tiene reservas registradas. Id del Campo: " + idTipo);
            throw new IllegalArgumentException(
                    "No se puede eliminar este campo. Se encuentra relacionado con una o mas reservas.");
        }
    }

    public void eliminar(Integer idTipo) {
        tipoCampoRespository.deleteById(idTipo);
    }

    public TipoCampo buscarById(Integer idTipo) {
        return tipoCampoRespository.findById(idTipo).orElse(null);
    }
}
