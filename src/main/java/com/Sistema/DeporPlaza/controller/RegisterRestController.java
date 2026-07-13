package com.Sistema.DeporPlaza.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Sistema.DeporPlaza.model.Rol;
import com.Sistema.DeporPlaza.model.Usuario;
import com.Sistema.DeporPlaza.repository.RolRepository;
import com.Sistema.DeporPlaza.repository.UsuarioRepository;
import com.Sistema.DeporPlaza.service.RolService;
import com.Sistema.DeporPlaza.service.UsuarioService;
import com.Sistema.DeporPlaza.specification.UsuarioSpecification;

@RestController
public class RegisterRestController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public Map<String, String> registrar(@ModelAttribute Usuario usuario) {
        Map<String, String> resultado = new HashMap<>();

        try {
            // Rol rol = rolRepository.findById(2).orElseThrow();
            Rol rol = rolRepository.findByNombreRol("USER");
            usuario.setRol(rol);
            usuarioService.validar(usuario);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioService.guardarUser(usuario);
            resultado.put("success", "Usuario registrado exitosamente");

        } catch (IllegalArgumentException e) {
            resultado.put("error", "Error al registrar el usuario: " + e.getMessage());
        } catch (Exception e) {
            resultado.put("error", "Error al registrar el usuario: " + e.getMessage());
        }

        return resultado;
    }

    @PostMapping("/admin/registrarUsuario")
    public Map<String, String> registrarAdmin(@ModelAttribute Usuario usuario, @RequestParam Integer idRol) {
        Map<String, String> resultado = new HashMap<>();

        try {
            usuarioService.guardarAdmin(usuario, idRol);
            resultado.put("success", "Operación Exitosa");

        } catch (IllegalArgumentException e) {
            resultado.put("error", "Error al registrar el usuario: " + e.getMessage());
        } catch (Exception e) {
            resultado.put("error", "Error al registrar el usuario: " + e.getMessage());
        }

        return resultado;
    }

    @PostMapping("/admin/eliminarUsuarioPorId")
    public Map<String, Object> eliminarUsuario(@RequestParam Integer idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.validarExistencia(idUsuario);
            usuarioService.eliminar(idUsuario);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("message", "Usuario eliminado correctamente");
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al eliminar: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/obtenerUsuarios")
    public Map<String, Object> obtenerUsuarios() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Usuario> usuarios = usuarioService.listar();
            System.out.println(usuarios);
            response.put("success", true);
            response.put("usuarios", usuarios);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/obtenerUsuarioById")
    public Map<String, Object> obtenerUsuarioById(@RequestParam Integer idUsuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = usuarioService.buscarById(idUsuario);
            System.out.println(usuario);
            response.put("success", true);
            response.put("usuario", usuario);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/admin/busquedaUsuario")
    public Map<String, Object> busqueda(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String apellidos,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String estado) {
        Map<String, Object> response = new HashMap<>();

        try {
            UsuarioSpecification specification = new UsuarioSpecification(dni, apellidos, email, telefono, rol, estado);
            List<Usuario> usuariosBusqueda = usuarioRepository.findAll(specification);
            // El reponse almacena claves valores
            response.put("success", true);
            response.put("usuarios", usuariosBusqueda);
        } catch (IllegalArgumentException e) {
            // En caso de error, se captura la excepción y se devuelve un mensaje de error
            response.put("success", false);
            response.put("message", "Error al carga los usuarios: " + e.getMessage());
        }
        return response;
    }
}
