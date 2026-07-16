package com.Sistema.DeporPlaza.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.model.Reserva;
import com.Sistema.DeporPlaza.model.Usuario;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ReservaSpecification implements Specification<Reserva> {

        private String dni;
        private String apellidos;
        private Integer idCampo;
        private LocalDate fechaReserva;
        private String estado;

        public ReservaSpecification(
                        String dni,
                        String apellidos,
                        Integer idCampo,
                        LocalDate fechaReserva,
                        String estado) {

                this.dni = dni;
                this.apellidos = apellidos;
                this.idCampo = idCampo;
                this.fechaReserva = fechaReserva;
                this.estado = estado;
        }

        @Override
        public @Nullable Predicate toPredicate(
                        Root<Reserva> root,
                        CriteriaQuery<?> query,
                        CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                // JOIN CON USUARIO
                Join<Reserva, Usuario> usuarioJoin = root.join("usuario");

                // JOIN CON CAMPO
                Join<Reserva, CampoDeportivo> campoJoin = root.join("campo");

                if (StringUtils.hasText(dni)) {

                        predicates.add(
                                        cb.like(
                                                        usuarioJoin.get("dni"),
                                                        "%" + dni + "%"));
                }

                if (StringUtils.hasText(apellidos)) {

                        predicates.add(
                                        cb.like(
                                                        cb.lower(
                                                                        usuarioJoin.get(
                                                                                        "apellidos")),
                                                        "%"
                                                                        + apellidos
                                                                                        .toLowerCase()
                                                                        + "%"));
                }

                if (idCampo != null) {
                        predicates.add(
                                        cb.equal(campoJoin.get("idCampo"), idCampo));
                }

                if (StringUtils.hasText(estado)) {

                        predicates.add(
                                        cb.equal(
                                                        cb.lower(
                                                                        root.get("estado")),
                                                        estado.toLowerCase()));
                }

                if (fechaReserva != null) {
                        predicates.add(
                                        cb.equal(root.get("fechaReserva"), fechaReserva));
                }

                return cb.and(
                                predicates.toArray(
                                                new Predicate[0]));
        }

}
