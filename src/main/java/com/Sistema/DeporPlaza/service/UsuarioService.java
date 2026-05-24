package com.Sistema.DeporPlaza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public void guardar(Usuario usuario) {
        repo.save(usuario);
    }

    public void eliminar(Integer idUsuario) {
        repo.deleteById(idUsuario);
    }

    public Usuario buscarById(Integer idUsuario) {
        return repo.findById(idUsuario).orElse(null);
    }

    public Usuario buscarByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Integer ultimoId() {
        Integer ultimoId = repo.findMaxId();
        if (ultimoId == null) {
            return 1;
        }
        return ultimoId + 1;
    }
}
