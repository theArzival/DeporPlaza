package com.Sistema.DeporPlaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Sistema.DeporPlaza.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT MAX (u.idUsuario) FROM Usuario u")
    Integer findMaxId();

    Usuario findByEmail(String email);

    Usuario findByDni(String dni);

}
