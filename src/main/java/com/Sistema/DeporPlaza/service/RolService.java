package com.Sistema.DeporPlaza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.Rol;
import com.Sistema.DeporPlaza.repository.RolRepository;

@Service
public class RolService {
    @Autowired
    private RolRepository repo;

    public List<Rol> listar() {
        return repo.findAll();
    }

    public void guardar(Rol rol) {
        repo.save(rol);
    }

    public void eliminar(Integer idRol) {
        repo.deleteById(idRol);
    }

    public Rol buscarById(Integer idRol) {
        return repo.findById(idRol).orElse(null);
    }

    public Rol buscarByNombreRol(String nombreRol) {
        return repo.findByNombreRol(nombreRol);
    }

    public Integer ultimoId() {
        Integer ultimoId = repo.findMaxId();
        if (ultimoId == null) {
            return 1;
        }
        return ultimoId + 1;
    }
}
