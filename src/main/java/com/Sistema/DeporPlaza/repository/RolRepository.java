package com.Sistema.DeporPlaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sistema.DeporPlaza.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombreRol(String nombreRol);
}
