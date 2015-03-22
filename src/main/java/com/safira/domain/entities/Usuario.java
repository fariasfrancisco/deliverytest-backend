package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario extends ModelEntity {

    @Column(nullable = false, unique = true)
    private String facebookId;
    @Column(nullable = false, unique = true)
    private String email;
    private String nombre;
    private String apellido;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private Set<Pedido> pedidos = new HashSet<>(0);

    public Usuario() {
        this(UUID.randomUUID());
    }

    public Usuario(UUID uuid) {
        super(uuid);
    }

    public Usuario(Builder builder) {
        super(UUID.randomUUID());
        this.facebookId = builder.facebookId;
        this.email = builder.email;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.pedidos = builder.pedidos;
        for (Pedido pedido : pedidos) {
            if (pedido.getUsuario() != this) {
                pedido.setUsuario(this);
            }
        }
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        for (Pedido pedido : pedidos) {
            if (pedido.getUsuario() != this) {
                pedido.setUsuario(this);
            }
        }
    }

    public static class Builder {
        private String facebookId;
        private String email;
        private String nombre;
        private String apellido;
        private Set<Pedido> pedidos = new HashSet<>(0);

        public Builder withFacebookId(String facebookId) {
            this.facebookId = facebookId;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder withPedidos(Set<Pedido> pedidos) {
            this.pedidos = pedidos;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }
}
