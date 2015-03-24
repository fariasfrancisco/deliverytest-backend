package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safira.domain.Estado;
import com.safira.domain.entity.ModelEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Pedido extends ModelEntity {

    private String telefono;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Type(type = "com.safira.common.LocalDateTimeUserType")
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Menu_Pedido", joinColumns = {
            @JoinColumn(name = "pedido_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    })
    private Set<Menu> menus = new HashSet<>();

    public Pedido() {
        this(UUID.randomUUID());
    }

    public Pedido(UUID uuid) {
        super(uuid);
    }

    public Pedido(Builder builder) {
        super(UUID.randomUUID());
        this.telefono = builder.telefono;
        this.estado = builder.estado;
        this.fecha = builder.fecha;
        this.direccion = builder.direccion;
        if (!direccion.getPedidos().contains(this)) direccion.getPedidos().add(this);
        this.usuario = builder.usuario;
        if (!usuario.getPedidos().contains(this)) usuario.getPedidos().add(this);
        this.restaurante = builder.restaurante;
        if (!restaurante.getPedidos().contains(this)) restaurante.getPedidos().add(this);
        this.menus = builder.menus;
        for (Menu menu : menus) {
            if (!menu.getPedidos().contains(this)) menu.getPedidos().add(this);
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (!usuario.getPedidos().contains(this)) {
            usuario.getPedidos().add(this);
        }
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (!restaurante.getPedidos().contains(this)) {
            restaurante.getPedidos().add(this);
        }
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public static class Builder {
        private String telefono;
        private Estado estado = Estado.PENDIENTE;
        private LocalDateTime fecha;
        private Direccion direccion;
        private Usuario usuario;
        private Restaurante restaurante;
        private Set<Menu> menus = new HashSet<>(0);

        public Builder withTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder withEstado(Estado estado) {
            this.estado = estado;
            return this;
        }

        public Builder withFecha(LocalDateTime fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder withDireccion(Direccion direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder withUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder withRestaurante(Restaurante restaurante) {
            this.restaurante = restaurante;
            return this;
        }

        public Builder withMenus(Set<Menu> menus) {
            this.menus = menus;
            return this;
        }

        public Pedido build() {
            return new Pedido(this);
        }
    }

    public String costoTotalAsString() {
        BigDecimal total = BigDecimal.ZERO;
        for (Menu menu : menus) {
            total = total.add(menu.getCosto());
        }
        return total.toString();
    }
}
