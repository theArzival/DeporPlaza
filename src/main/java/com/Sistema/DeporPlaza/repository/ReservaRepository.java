package com.Sistema.DeporPlaza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.Sistema.DeporPlaza.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>, JpaSpecificationExecutor<Reserva> {
        // Se coloca Campo porque JPA mapea segun la entidad Reserva y en ella no hay
        // id, sino Campo.idCampo
        List<Reserva> findByCampoIdCampo(Integer idCampo);

        List<Reserva> findByCampoIdCampoAndEstado(Integer idCampo, String estado);

        boolean existsByCampoIdCampo(Integer idCampo);

        boolean existsByUsuarioIdUsuario(Integer idUsuario);

        List<Reserva> findTop5ByOrderByIdReservaDesc();

        @Query("""
                        SELECT MONTH(r.fechaReserva), COUNT(r)
                        FROM Reserva r
                        GROUP BY MONTH(r.fechaReserva)
                        ORDER BY MONTH(r.fechaReserva)
                        """)
        List<Object[]> reservasPorMes();

        @Query("""
                        SELECT r.campo.tipoCampo.nombreTipo, COUNT(r)
                        FROM Reserva r
                        GROUP BY r.campo.tipoCampo.nombreTipo
                        ORDER BY COUNT(r) DESC
                        """)
        List<Object[]> reservasPorTipo();
}
