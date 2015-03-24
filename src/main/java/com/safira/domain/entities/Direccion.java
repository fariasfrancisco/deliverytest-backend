package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by francisco on 23/03/15.
 */
@Entity
public class Direccion extends ModelEntity {
    private String calle;
    private String numero;
    private String piso;
    private String departamento;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "direccion")
    private Set<Pedido> pedidos = new HashSet<>();

    public Direccion() {
        this(UUID.randomUUID());
    }

    public Direccion(UUID uuid) {
        super(uuid);
    }

    public Direccion(Builder builder) {
        super(UUID.randomUUID());
        this.calle = builder.calle;
        this.numero = builder.numero;
        this.piso = builder.piso;
        this.departamento = builder.departamento;
        this.usuario = builder.usuario;
        if (!usuario.getDirecciones().contains(this)) usuario.getDirecciones().add(this);
        this.pedidos = builder.pedidos;
        for (Pedido pedido : pedidos) {
            if (pedido.getDireccion() != this) pedido.setDireccion(this);
        }
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (!usuario.getDirecciones().contains(this)) usuario.getDirecciones().add(this);
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        for (Pedido pedido : pedidos) {
            if (pedido.getDireccion() != this) pedido.setDireccion(this);
        }
    }

    private static class Builder {
        private String calle;
        private String numero;
        private String piso;
        private String departamento;
        private Usuario usuario;
        private Set<Pedido> pedidos = new HashSet<>();

        public Builder withCalle(String calle) {
            this.calle = calle;
            return this;
        }

        public Builder withNumero(String numero) {
            this.numero = numero;
            return this;
        }

        public Builder withPiso(String piso) {
            this.piso = piso;
            return this;
        }

        public Builder withDepartamento(String departamento) {
            this.departamento = departamento;
            return this;
        }

        public Builder withUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder withPedidos(Set<Pedido> pedidos) {
            this.pedidos = pedidos;
            return this;
        }

        public Direccion build() {
            return new Direccion(this);
        }
    }
}
