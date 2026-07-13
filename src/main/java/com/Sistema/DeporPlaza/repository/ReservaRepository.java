package com.Sistema.DeporPlaza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sistema.DeporPlaza.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    // Se coloca Campo porque JPA mapea segun la entidad Reserva y en ella no hay
    // id, sino Campo.idCampo
    List<Reserva> findByCampoIdCampo(Integer idCampo);

    boolean existsByCampoIdCampo(Integer idCampo);

    boolean existsByUsuarioIdUsuario(Integer idUsuario);
}
