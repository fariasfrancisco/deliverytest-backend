package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Usuario extends ModelEntity {

    @Column(nullable = false, unique = true)
    private String facebookId;
    @Column(nullable = false, unique = true)
    private String email;
    private String nombre;
    private String apellido;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private Set<Pedido> pedidos = new HashSet<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private Set<Direccion> direcciones = new HashSet<>();

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
        this.direcciones = builder.direcciones;
        for (Direccion direccion : direcciones) {
            if (direccion.getUsuario() != this) {
                direccion.setUsuario(this);
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

    public Set<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(Set<Direccion> direcciones) {
        this.direcciones = direcciones;
        for (Direccion direccion : direcciones) {
            if (direccion.getUsuario() != this) {
                direccion.setUsuario(this);
            }
        }
    }

    public static class Builder {
        private String facebookId;
        private String email;
        private String nombre;
        private String apellido;
        private Set<Pedido> pedidos = new HashSet<>();
        private Set<Direccion> direcciones = new HashSet<>();

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

        public Builder withDirecciones(Set<Direccion> direcciones) {
            this.direcciones = direcciones;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }
}
