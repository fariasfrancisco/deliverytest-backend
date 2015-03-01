package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safira.service.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Francisco on 26/02/2015.
 */

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    private int id;
    private String calle;
    private String numero;
    private String piso;
    private String departamento;
    private String telefono;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate fecha;
    private Usuario usuario;
    private Restaurante restaurante;
    private Set<Menu> menus = new HashSet<>(0);

    public Pedido() {
    }

    public Pedido(Builder builder) {
        this.calle = builder.calle;
        this.numero = builder.numero;
        this.piso = builder.piso;
        this.departamento = builder.departamento;
        this.telefono = builder.telefono;
        this.fecha = builder.fecha;
        this.usuario = builder.usuario;
        this.restaurante = builder.restaurante;
        this.menus = builder.menus;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PedidoId", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "calle", nullable = false)
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Column(name = "numero", nullable = false)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "piso", nullable = true)
    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    @Column(name = "departamento", nullable = true)
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Column(name = "telefono", nullable = false)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(name = "fecha", nullable = false)
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UsuarioId", nullable = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RestauranteId", nullable = false)
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "menu_pedido", joinColumns = {
            @JoinColumn(name = "PedidoId", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "MenuId", nullable = false, updatable = false)})
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public static class Builder {
        private String calle;
        private String numero;
        private String piso;
        private String departamento;
        private String telefono;
        private LocalDate fecha;
        private Usuario usuario;
        private Restaurante restaurante;
        private Set<Menu> menus = new HashSet<>(0);

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

        public Builder withDeepartamento(String departamento) {
            this.departamento = departamento;
            return this;
        }

        public Builder withTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder withFecha(LocalDate fecha) {
            this.fecha = fecha;
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

        public Builder withMenus(Set<Menu> menus) {
            this.usuario = usuario;
            return this;
        }

        public Pedido build() {
            return new Pedido(this);
        }
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", piso='" + piso + '\'' +
                ", departamento='" + departamento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fecha=" + fecha.toString() +
                '}';
    }
}
