package com.Sistema.DeporPlaza.service;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.Rol;
import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.ReservaRepository;
import com.Sistema.DeporPlaza.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private RolService rolService;
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public List<Object[]> obtenerUsuariosPorMes() {
        return repo.usuariosPorMes();
    }

    public Long contar() {
        return repo.count();
    }

    public void guardarAdmin(Usuario usuario, Integer idRol) {
        Rol rol = rolService.buscarById(idRol);
        usuario.setRol(rol);
        if (usuario.getIdUsuario() == null) {
            validar(usuario);

            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            repo.save(usuario);
            log.info("Usuario registrado por Admin correctamente. Correo: {}, DNI: {}, Nombres: {}", usuario.getEmail(),
                    usuario.getDni(), usuario.getApellidos().concat(usuario.getNombres()));

        } else {
            validar(usuario);
            Usuario usuarioBd = buscarById(usuario.getIdUsuario());
            usuarioBd.setDni(usuario.getDni());
            usuarioBd.setNombres(usuario.getNombres());
            usuarioBd.setApellidos(usuario.getApellidos());
            usuarioBd.setEmail(usuario.getEmail());
            usuarioBd.setTelefono(usuario.getTelefono());
            usuarioBd.setEstado(usuario.getEstado());
            usuarioBd.setRol(usuario.getRol());
            log.info("Usuario registrado por Admin correctamente. Correo: {}, DNI: {}, Nombres: {}", usuario.getEmail(),
                    usuario.getDni(), usuario.getApellidos().concat(usuario.getNombres()));

            repo.save(usuarioBd);
        }

    }

    public void guardarUser(Usuario usuario) {
        repo.save(usuario);
        log.info("Usuario auto registrado correctamente. Correo: {}", usuario.getEmail());
    }

    public void eliminar(Integer idUsuario) {
        repo.deleteById(idUsuario);
        log.info("Usuario eliminado correctamente. IdUsuario: {}", idUsuario);
    }

    public Usuario buscarById(Integer idUsuario) {
        log.info("Buscando usuario con ID: {}", idUsuario);
        return repo.findById(idUsuario).orElse(null);
    }

    // Verifica si ya hay un DNI existente al Registrar
    public void existeDniRegistrado(String dni) {
        log.info("Buscando existencia de usuario con DNI: {}", dni);
        if (repo.existsByDni(dni)) {
            throw new IllegalArgumentException("El DNI ya se encuentra registrado.");
        }
    }

    // Verfica si existe un cuenta con el DNI asociado al RESERVAR
    public void existeDniEnBD(String dni) {
        log.info("Buscando existencia de usuario con DNI registrado: {}", dni);
        if (!repo.existsByDni(dni)) {
            throw new IllegalArgumentException("El DNI no esta asociado a ninguna cuenta.");
        }
    }

    public void existsByEmail(String correo) {
        if (repo.existsByEmail(correo)) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado.");

        }
    }

    public Usuario buscarByDni(String dni) {
        log.info("Buscando usuario con dni: {}", dni);
        Usuario resultado = repo.findByDni(dni);
        // Verifica si existe
        if (resultado == null) {
            log.error("El usuario con DNI:{} no existe en la BD", dni);
            throw new IllegalArgumentException("El usuario no esta registrado");
        }
        return resultado;
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

        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
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

        if (usuario.getIdUsuario() == null) {
            if (StringUtils.isBlank(usuario.getPassword())) {
                log.warn("Contraseña vacía");
                throw new IllegalArgumentException("La contraseña es obligatoria");
            }

            if (StringUtils.length(usuario.getPassword()) < 8) {
                log.warn("Contraseña demasiado corta");
                throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
            }
        }
    }

    public void validarExistencia(Integer idUsuario) {
        boolean usuarioExiste = reservaRepository.existsByUsuarioIdUsuario(idUsuario);

        if (usuarioExiste) {
            log.error("Se intento un usuario con reservas registradas. Id del Usuario: " + idUsuario);
            throw new IllegalArgumentException(
                    "No se puede eliminar este usuario. Se encuentra relacionado con una o mas reservas.");
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
