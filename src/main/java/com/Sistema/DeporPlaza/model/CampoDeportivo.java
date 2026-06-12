package com.Sistema.DeporPlaza.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "campo_deportivo")
public class CampoDeportivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campo")
    private Integer idCampo;

    @Column(name = "nombre_campo")
    private String nombreCampo;

    @Column(name = "precio_hora")
    private BigDecimal precioHora;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private String estado = "ACTIVO";

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoCampo tipoCampo;

    public CampoDeportivo() {
    }

    public CampoDeportivo(String descripcion, Integer idCampo, String nombreCampo, BigDecimal precioHora,
            TipoCampo tipoCampo) {
        this.descripcion = descripcion;
        this.idCampo = idCampo;
        this.nombreCampo = nombreCampo;
        this.precioHora = precioHora;
        this.tipoCampo = tipoCampo;
    }

    public Integer getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(Integer idCampo) {
        this.idCampo = idCampo;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public BigDecimal getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(BigDecimal precioHora) {
        this.precioHora = precioHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TipoCampo getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(TipoCampo tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

}