package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Francisco on 21/02/2015.
 */

@Entity
@Table(name = "restaurantes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "direccion"),
        @UniqueConstraint(columnNames = "telefono")})
public class Restaurante implements Serializable {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private RestauranteLogin restauranteLogin;
    private Set<Menu> menus = new HashSet<>(0);
    private Set<Pedido> pedidos = new HashSet<>(0);

    public Restaurante() {
    }

    public Restaurante(Builder builder) {
        this.nombre = builder.nombre;
        this.direccion = builder.direccion;
        this.telefono = builder.telefono;
        this.email = builder.email;
        this.restauranteLogin = builder.restauranteLogin;
        this.menus = builder.menus;
        this.pedidos = builder.pedidos;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RestauranteId", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nombre", nullable = false, length = 30)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "direccion", nullable = false, length = 30)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "telefono", nullable = false, length = 16)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(name = "email", nullable = false, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "restaurante", cascade = CascadeType.ALL)
    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }

    public void setRestauranteLogin(RestauranteLogin restauranteLogin) {
        this.restauranteLogin = restauranteLogin;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public static class Builder {

        private String nombre;
        private String direccion;
        private String telefono;
        private String email;
        private RestauranteLogin restauranteLogin;
        private Set<Menu> menus = new HashSet<>();
        private Set<Pedido> pedidos = new HashSet<>(0);

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder withTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withRestauranteLogin(RestauranteLogin restauranteLogin) {
            this.restauranteLogin = restauranteLogin;
            return this;
        }

        public Builder withMenus(Set<Menu> menus) {
            this.menus = menus;
            return this;
        }

        public Builder withPedidos(Set<Pedido> pedidos) {
            this.pedidos = pedidos;
            return this;
        }

        public Restaurante build() {
            return new Restaurante(this);
        }

    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getIdAsString() {
        return String.valueOf(id);
    }
}
