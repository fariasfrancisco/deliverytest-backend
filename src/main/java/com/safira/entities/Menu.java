package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Francisco on 21/02/2015.
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal costo;
    private Restaurante restaurante;
    private Set<Pedido> pedidos = new HashSet<>();

    public Menu() {
    }

    public Menu(Builder builder) {
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.costo = builder.costo;
        this.restaurante = builder.restaurante;
        this.pedidos = builder.pedidos;
    }

    public Menu(Menu menu) {
        this.nombre = menu.nombre;
        this.descripcion = menu.descripcion;
        this.costo = menu.costo;
        this.restaurante = menu.restaurante;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RestauranteId", nullable = false)
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante Restaurante) {
        this.restaurante = restaurante;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "menus")
    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public static class Builder {
        private String nombre;
        private String descripcion;
        private BigDecimal costo;
        private Restaurante restaurante;
        private Set<Pedido> pedidos = new HashSet<>();

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder withCosto(BigDecimal costo) {
            this.costo = costo;
            return this;
        }

        public Builder withRestaurante(Restaurante restaurante) {
            this.restaurante = restaurante;
            return this;
        }

        public Builder withPedidos(Set<Pedido> pedidos) {
            this.pedidos = pedidos;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
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

    public String getIdAsString() {
        return String.valueOf(id);
    }

    public String getCostoAsString() {
        return costo.toString();
    }
}
