package com.Sistema.DeporPlaza.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.Sistema.DeporPlaza.model.CampoDeportivo;
import com.Sistema.DeporPlaza.model.TipoCampo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class campoSpecification implements Specification<CampoDeportivo> {
    private String nombreCampo;
    private BigDecimal precioHoraMin;
    private BigDecimal precioHoraMax;
    private String tipoCampo;
    private String estado;

    public campoSpecification(String nombreCampo, BigDecimal precioHoraMin, BigDecimal precioHoraMax, String tipoCampo,
            String estado) {
        this.nombreCampo = nombreCampo;
        this.precioHoraMin = precioHoraMin;
        this.precioHoraMax = precioHoraMax;
        this.tipoCampo = tipoCampo;
        this.estado = estado;
    }

    @Override
    public @Nullable Predicate toPredicate(Root<CampoDeportivo> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        /*
         * ROOT = SELECT P FRON PRODUCT P
         * QUERY = ORDER BY, ASECEN,ETC
         * CRITERIABUILDES = WHERE, LIKE ETC.
         */
        if (StringUtils.hasText(nombreCampo)) {
            Expression<String> nombreCampoToLowerCase = criteriaBuilder.lower(root.get("nombreCampo"));
            Predicate nombreCampoLikePredicate = criteriaBuilder.like(nombreCampoToLowerCase,
                    "%".concat(nombreCampo).concat("%"));
            predicates.add(nombreCampoLikePredicate);
        }
        if (precioHoraMin != null && !precioHoraMin.equals(BigDecimal.ZERO)) {
            Predicate precioGreaterThanEqualsPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("precioHora"),
                    precioHoraMin);
            predicates.add(precioGreaterThanEqualsPredicate);

        }
        if (precioHoraMax != null && !precioHoraMax.equals(BigDecimal.ZERO)) {
            Predicate precioLessThanEqualsPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("precioHora"),
                    precioHoraMax);
            predicates.add(precioLessThanEqualsPredicate);
        }
        Join<CampoDeportivo, TipoCampo> campoTipoJoin = root.join("tipoCampo");
        if (StringUtils.hasText(tipoCampo)) {
            Expression<String> nombreTipoToLowerCase = criteriaBuilder.lower(campoTipoJoin.get("nombreTipo"));
            Predicate nombreTipoLikePredicate = criteriaBuilder.like(nombreTipoToLowerCase,
                    "%".concat(tipoCampo).concat("%"));
            predicates.add(nombreTipoLikePredicate);
        }
        // Esto es en caso lo quiera buscar por ID TENDRIA QUE MODIFICAR LOS ATRIBUTOS
        // Join<CampoDeportivo, TipoCampo> campoTipoJoin2 = root.join("tipoCampo");

        // predicates.add(
        // criteriaBuilder.equal(campoTipoJoin.get("idTipo"), idTipo));
        if (StringUtils.hasText(estado)) {
            Predicate estadoPredicate = criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("estado")),
                    estado.toLowerCase());
            predicates.add(estadoPredicate);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
