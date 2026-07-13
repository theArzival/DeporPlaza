package com.Sistema.DeporPlaza.specification;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.Sistema.DeporPlaza.model.Rol;
import com.Sistema.DeporPlaza.model.Usuario;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UsuarioSpecification implements Specification<Usuario> {
    private String dni;
    private String apellidos;
    private String email;
    private String telefono;
    private String rol;
    private String estado;

    public UsuarioSpecification(String dni, String apellidos,
            String email, String telefono,
            String rol, String estado) {

        this.dni = dni;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.estado = estado;
    }

    @Override
    public @Nullable Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(dni)) {
            predicates.add(
                    cb.like(root.get("dni"), "%" + dni + "%"));
        }

        if (StringUtils.hasText(apellidos)) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("apellidos")),
                            "%" + apellidos.toLowerCase() + "%"));
        }

        if (StringUtils.hasText(email)) {
            predicates.add(
                    cb.like(
                            cb.lower(root.get("email")),
                            "%" + email.toLowerCase() + "%"));
        }

        if (StringUtils.hasText(telefono)) {
            predicates.add(
                    cb.like(root.get("telefono"),
                            "%" + telefono + "%"));
        }

        if (StringUtils.hasText(estado)) {
            predicates.add(
                    cb.equal(
                            cb.lower(root.get("estado")),
                            estado.toLowerCase()));
        }

        if (StringUtils.hasText(rol)) {
            Join<Usuario, Rol> usuarioRolJoin = root.join("rol");
            predicates.add(
                    cb.equal(usuarioRolJoin.get("idRol"), rol));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

}
