package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Menu extends ModelEntity {
    private String nombre;
    private String descripcion;
    private BigDecimal costo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "menus")
    private Set<Pedido> pedidos = new HashSet<>();

    public Menu() {
        this(UUID.randomUUID());
    }

    public Menu(UUID uuid) {
        super(UUID.randomUUID());
    }

    public Menu(Builder builder) {
        super(UUID.randomUUID());
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.costo = builder.costo;
        this.restaurante = builder.restaurante;
        if (!restaurante.getMenus().contains(this)) {
            restaurante.getMenus().add(this);
        }
        this.pedidos = builder.pedidos;
        for (Pedido pedido : pedidos) {
            if (!pedido.getMenus().contains(this)) {
                pedido.getMenus().add(this);
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (!restaurante.getMenus().contains(this)) {
            restaurante.getMenus().add(this);
        }
    }

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
        final StringBuffer sb = new StringBuffer("Menu{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append(", costo=").append(costo);
        sb.append('}');
        return sb.toString();
    }
}
