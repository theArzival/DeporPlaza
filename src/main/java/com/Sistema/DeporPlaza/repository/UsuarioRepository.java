package com.Sistema.DeporPlaza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.Sistema.DeporPlaza.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {
    @Query("SELECT MAX (u.idUsuario) FROM Usuario u")
    Integer findMaxId();

    Usuario findByEmail(String email);

    Usuario findByDni(String dni);

    List<Usuario> findAll();

    boolean existsByDni(String dni);

    boolean existsByEmail(String correo);

    boolean existsByIdUsuario(Integer idUsuario);
}
