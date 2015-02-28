package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Francisco on 21/02/2015.
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable {
    private int id;
    private Restaurante restaurante;
    private String nombre;
    private String descripcion;
    private BigDecimal costo;

    public Menu() {
    }

    public Menu(String nombre, String descripcion, BigDecimal costo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public Menu(Restaurante restaurante, String nombre, String descripcion, BigDecimal costo) {
        this.restaurante = restaurante;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MenuId", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RestauranteId", nullable = false)
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante Restaurante) {
        this.restaurante = restaurante;
    }

    @Column(name = "nombre", unique = true, nullable = false, length = 30)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "descripcion", unique = true, nullable = false)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "costo", unique = true, nullable = false, precision = 2)
    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costo=" + costo +
                '}';
    }
}
