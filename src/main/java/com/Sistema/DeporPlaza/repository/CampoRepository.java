package com.Sistema.DeporPlaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sistema.DeporPlaza.model.CampoDeportivo;

public interface CampoRepository extends JpaRepository<CampoDeportivo, Integer> {
    CampoDeportivo findByNombreCampo(String nombreCampo);

}
