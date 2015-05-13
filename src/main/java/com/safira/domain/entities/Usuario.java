package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private Set<Pedido> pedidos = new HashSet<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private Set<Direccion> direcciones = new HashSet<>();

    public Usuario() {
        this(UUID.randomUUID().toString());
    }

    public Usuario(String uuid) {
        super(uuid);
    }

    public Usuario(Builder builder) {
        super(UUID.randomUUID().toString());
        this.facebookId = builder.facebookId;
        this.email = builder.email;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        pedidos.stream()
                .filter(pedido -> pedido.getUsuario() != this)
                .forEach(pedido -> pedido.setUsuario(this));
    }

    public Set<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(Set<Direccion> direcciones) {
        this.direcciones = direcciones;
        direcciones.stream()
                .filter(direccion -> direccion.getUsuario() != this)
                .forEach(direccion -> direccion.setUsuario(this));
    }

    public static class Builder {
        private String facebookId;
        private String email;
        private String nombre;
        private String apellido;

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

        public Usuario build() {
            return new Usuario(this);
        }
    }
}
