package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Francisco on 26/02/2015.
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "facebookId"),
        @UniqueConstraint(columnNames = "email")})
public class Usuario implements Serializable {
    private int id;
    private String facebookId;
    private String email;
    private String nombre;
    private String apellido;
    private Set<Pedido> pedidos = new HashSet<>(0);

    public Usuario() {
    }

    public Usuario(Builder builder) {
        this.facebookId = builder.facebookId;
        this.email = builder.email;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.pedidos = builder.pedidos;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "UsuarioId", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "facebookId", nullable = false)
    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "nombre", nullable = false)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "apellido", nullable = false)
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", facebookId='" + facebookId + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }

    public String getIdAsString(){
        return String.valueOf(id);
    }
}
