package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "restaurantes")
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
        if (restauranteLogin != null && restauranteLogin.getRestaurante() != this) {
            restauranteLogin.setRestaurante(this);
        }
        this.menus = builder.menus;
        for (Menu menu : menus) {
            if (menu.getRestaurante() != this) {
                menu.setRestaurante(this);
            }
        }
        this.pedidos = builder.pedidos;
        for (Pedido pedido : pedidos) {
            if (pedido.getRestaurante() != this) {
                pedido.setRestaurante(this);
            }
        }
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "restaurante_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nombre", nullable = false, length = 50)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "direccion", nullable = false, length = 50, unique = true)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "telefono", nullable = false, length = 16, unique = true)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "restaurante", cascade = CascadeType.ALL)
    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }

    public void setRestauranteLogin(RestauranteLogin restauranteLogin) {
        this.restauranteLogin = restauranteLogin;
        if (restauranteLogin.getRestaurante() != this) {
            restauranteLogin.setRestaurante(this);
        }
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurante")
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
        for (Menu menu : menus) {
            if (menu.getRestaurante() != this) {
                menu.setRestaurante(this);
            }
        }
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurante")
    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        for (Pedido pedido : pedidos) {
            if (pedido.getRestaurante() != this) {
                pedido.setRestaurante(this);
            }
        }
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
                ", usuario='" + restauranteLogin.getUsuario() + '\'' +
                '}';
    }
}