package com.Sistema.DeporPlaza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.repository.CampoRepository;

@Service
public class CampoService {
    @Autowired
    private CampoRepository repo;

    public List<CampoDeportivo> listar() {
        return repo.findAll();
    }

    public void guardar(CampoDeportivo rol) {
        repo.save(rol);
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
