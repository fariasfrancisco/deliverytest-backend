package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.pedido", cascade = CascadeType.ALL)
    private Set<MenuPedido> menuPedidos = new HashSet<>();

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
        if (!usuario.getPedidos().contains(this)) usuario.getPedidos().add(this);
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (!restaurante.getPedidos().contains(this)) restaurante.getPedidos().add(this);
    }

    public Set<MenuPedido> getMenuPedidos() {
        return menuPedidos;
    }

    public void setMenuPedidos(Set<MenuPedido> menuPedidos) {
        this.menuPedidos = menuPedidos;
    }

    public static class Builder {
        private String telefono;
        private Estado estado = Estado.PENDIENTE;
        private LocalDateTime fecha;
        private Direccion direccion;
        private Usuario usuario;
        private Restaurante restaurante;

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

        public Pedido build() {
            return new Pedido(this);
        }
    }

    public String costoTotalAsString() {
        BigDecimal total = BigDecimal.ZERO;
        return total.toString();
    }

    public enum Estado {
        PENDIENTE, ACEPTADO, RECHAZADO, ENVIADO, RECIBIDO
    }
}
