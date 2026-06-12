package com.Sistema.DeporPlaza.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer idReserva;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_campo")
    private CampoDeportivo campo;

    @ManyToOne
    @JoinColumn(name = "id_horario")
    private Horario horario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    @Column(name = "estado")
    private String estado = "RESERVADO";

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public Reserva() {
    }

    public Reserva(CampoDeportivo campo, LocalDateTime fechaRegistro, LocalDate fechaReserva, Horario horario,
            Integer idReserva, String observaciones, Usuario usuario) {
        this.campo = campo;
        this.fechaRegistro = fechaRegistro;
        this.fechaReserva = fechaReserva;
        this.horario = horario;
        this.idReserva = idReserva;
        this.observaciones = observaciones;
        this.usuario = usuario;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CampoDeportivo getCampo() {
        return campo;
    }

    public void setCampo(CampoDeportivo campo) {
        this.campo = campo;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
