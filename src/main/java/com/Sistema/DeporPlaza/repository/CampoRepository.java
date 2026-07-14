package com.Sistema.DeporPlaza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.Sistema.DeporPlaza.model.CampoDeportivo;

public interface CampoRepository
        extends JpaRepository<CampoDeportivo, Integer>, JpaSpecificationExecutor<CampoDeportivo> {
    CampoDeportivo findByNombreCampo(String nombreCampo);

    List<CampoDeportivo> findAll();

    boolean existsByTipoCampoIdTipo(Integer idTipo);

}
