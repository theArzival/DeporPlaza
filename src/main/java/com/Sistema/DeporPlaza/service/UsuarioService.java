package com.Sistema.DeporPlaza.service;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public void guardar(Usuario usuario) {

        repo.save(usuario);
        log.info("Usuario registrado correctamente. Correo: {}", usuario.getEmail());
    }

    public void eliminar(Integer idUsuario) {
        repo.deleteById(idUsuario);
    }

    public Usuario buscarById(Integer idUsuario) {
        log.info("Buscando usuario con ID: {}", idUsuario);
        return repo.findById(idUsuario).orElse(null);
    }

    public Usuario buscarByDni(String dni) {
        log.info("Buscando usuario con DNI: {}", dni);
        return repo.findByDni(dni);
    }

    public Usuario buscarByEmail(String email) {
        log.info("Buscando usuario con correo: {}", email);
        return repo.findByEmail(email);
    }

    public void validar(Usuario usuario) {
        // USO DE APACHE COMMONS LANG
        if (ObjectUtils.isEmpty(usuario)) {
            // LOGBACK
            log.error("Se intentó registrar un usuario nulo");
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        if (StringUtils.isBlank(usuario.getNombres())) {
            log.warn("Nombre vacío");
            throw new IllegalArgumentException("Los nombres son obligatorios");
        }

        if (StringUtils.isBlank(usuario.getApellidos())) {
            log.warn("Apellidos vacíos");
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }

        if (StringUtils.isBlank(usuario.getDni())) {
            log.warn("DNI vacío");
            throw new IllegalArgumentException("El DNI es obligatorio");
        }

        if (!StringUtils.isNumeric(usuario.getDni())) {
            log.warn("DNI inválido: {}", usuario.getDni());
            throw new IllegalArgumentException("El DNI solo debe contener números");
        }

        if (StringUtils.length(usuario.getDni()) != 8) {
            log.warn("DNI con longitud incorrecta: {}", usuario.getDni());
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos");
        }

        if (StringUtils.isBlank(usuario.getEmail())) {
            log.warn("Correo vacío");
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        if (!StringUtils.contains(usuario.getEmail(), "@")) {
            log.warn("Correo inválido: {}", usuario.getEmail());
            throw new IllegalArgumentException("Correo electrónico inválido");
        }

        if (StringUtils.isBlank(usuario.getTelefono())) {
            log.warn("Teléfono vacío");
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }

        if (!StringUtils.isNumeric(usuario.getTelefono())) {
            log.warn("Teléfono inválido: {}", usuario.getTelefono());
            throw new IllegalArgumentException("El teléfono solo debe contener números");
        }

        if (StringUtils.isBlank(usuario.getPassword())) {
            log.warn("Contraseña vacía");
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (StringUtils.length(usuario.getPassword()) < 8) {
            log.warn("Contraseña demasiado corta");
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
    }

    public Integer ultimoId() {
        Integer ultimoId = repo.findMaxId();
        if (ultimoId == null) {
            return 1;
        }
        return ultimoId + 1;
    }
}
