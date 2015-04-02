package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @JsonManagedReference
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "restaurante", cascade = CascadeType.ALL)
    private RestauranteLogin restauranteLogin;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurante")
    private Set<Menu> menus = new HashSet<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurante")
    private Set<Pedido> pedidos = new HashSet<>();

    public Restaurante() {
        this(UUID.randomUUID());
    }

    public Restaurante(UUID uuid) {
        super(uuid);
    }

    public Restaurante(Builder builder) {
        super(UUID.randomUUID());
        this.nombre = builder.nombre;
        this.calle = builder.calle;
        this.numero = builder.numero;
        this.telefono = builder.telefono;
        this.email = builder.email;
        this.restauranteLogin = builder.restauranteLogin;
        if (restauranteLogin != null && restauranteLogin.getRestaurante() != this)
            restauranteLogin.setRestaurante(this);
        this.menus = builder.menus;
        menus.stream()
                .filter(menu -> menu.getRestaurante() != this)
                .forEach(menu -> menu.setRestaurante(this));
        this.pedidos = builder.pedidos;
        pedidos.stream()
                .filter(pedido -> pedido.getRestaurante() != this)
                .forEach(pedido -> pedido.setRestaurante(this));
    }

    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }

    public void setRestauranteLogin(RestauranteLogin restauranteLogin) {
        this.restauranteLogin = restauranteLogin;
        if (restauranteLogin.getRestaurante() != this) restauranteLogin.setRestaurante(this);
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public static class Builder {
        private String nombre;
        private String calle;
        private String numero;
        private String telefono;
        private String email;
        private RestauranteLogin restauranteLogin;
        private Set<Menu> menus = new HashSet<>();
        private Set<Pedido> pedidos = new HashSet<>();

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Restaurante that = (Restaurante) o;

        if (!nombre.equals(that.nombre)) return false;
        if (!calle.equals(that.calle)) return false;
        if (!numero.equals(that.numero)) return false;
        if (!telefono.equals(that.telefono)) return false;
        return email.equals(that.email);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + calle.hashCode();
        result = 31 * result + numero.hashCode();
        result = 31 * result + telefono.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Restaurante{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", calle='").append(calle).append('\'');
        sb.append(", numero='").append(numero).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
