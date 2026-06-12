package com.Sistema.DeporPlaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Sistema.DeporPlaza.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombreRol(String nombreRol);

    @Query("SELECT MAX (u.idRol) FROM Rol u")
    Integer findMaxId();
}
