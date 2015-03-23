package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Restaurante extends ModelEntity {

    @Column(nullable = false, unique = true)
    private String nombre;
    private String calle;
    private String numero;
    @Column(nullable = false, unique = true)
    private String telefono;
    private String email;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "restaurante", cascade = CascadeType.ALL)
    private RestauranteLogin restauranteLogin;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurante")
    private Set<Menu> menus = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurante")
    private Set<Pedido> pedidos = new HashSet<>();

    public Restaurante() {
        this(UUID.randomUUID());
    }

    public Restaurante(UUID uuid) {
        super(UUID.randomUUID());
    }

    public Restaurante(Builder builder) {
        super(UUID.randomUUID());
        this.nombre = builder.nombre;
        this.calle = builder.calle;
        this.numero = builder.numero;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }

    public void setRestauranteLogin(RestauranteLogin restauranteLogin) {
        this.restauranteLogin = restauranteLogin;
        if (restauranteLogin.getRestaurante() != this) {
            restauranteLogin.setRestaurante(this);
        }
    }

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
        private String calle;
        private String numero;
        private String telefono;
        private String email;
        private RestauranteLogin restauranteLogin;
        private Set<Menu> menus = new HashSet<>();
        private Set<Pedido> pedidos = new HashSet<>(0);

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withCalle(String calle) {
            this.calle = calle;
            return this;
        }

        public Builder withNumero(String numero) {
            this.numero = numero;
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
        final StringBuffer sb = new StringBuffer("Restaurante{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", calle='").append(calle).append('\'');
        sb.append(", numero='").append(numero).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}