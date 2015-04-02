package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "menus")
    private Set<Pedido> pedidos = new HashSet<>();

    public Menu() {
        this(UUID.randomUUID());
    }

    public Menu(UUID uuid) {
        super(uuid);
    }

    public Menu(Builder builder) {
        super(UUID.randomUUID());
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.costo = builder.costo;
        this.restaurante = builder.restaurante;
        if (!restaurante.getMenus().contains(this)) restaurante.getMenus().add(this);
        this.pedidos = builder.pedidos;
        pedidos.stream()
                .filter(pedido -> !pedido.getMenus().contains(this))
                .forEach(pedido -> pedido.getMenus().add(this));
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (!restaurante.getMenus().contains(this)) restaurante.getMenus().add(this);
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Menu menu = (Menu) o;

        if (!nombre.equals(menu.nombre)) return false;
        if (!descripcion.equals(menu.descripcion)) return false;
        return costo.equals(menu.costo);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + costo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("restaurante=").append(restaurante);
        sb.append(", costo=").append(costo);
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
