package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime fecha;
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
        if (!usuario.getPedidos().contains(this)) {
            usuario.getPedidos().add(this);
        }
        this.restaurante = builder.restaurante;
        if (!restaurante.getPedidos().contains(this)) {
            restaurante.getPedidos().add(this);
        }
        this.menus = builder.menus;
        for (Menu menu : menus) {
            if (!menu.getPedidos().contains(this)) {
                menu.getPedidos().add(this);
            }
        }
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "pedido_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "calle", nullable = false, length = 50)
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Column(name = "numero", nullable = false, length = 5)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "piso", nullable = true, length = 3)
    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    @Column(name = "departamento", nullable = true, length = 3)
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Column(name = "telefono", nullable = false, length = 16)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Type(type = "com.safira.service.hibernate.LocalDateTimeUserType")
    @Column(name = "fecha", nullable = false)
    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (!usuario.getPedidos().contains(this)) {
            usuario.getPedidos().add(this);
        }
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (!restaurante.getPedidos().contains(this)) {
            restaurante.getPedidos().add(this);
        }
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "menu_pedido", joinColumns = {
            @JoinColumn(name = "pedido_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    })
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
        private LocalDateTime fecha;
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

        public Builder withDepartamento(String departamento) {
            this.departamento = departamento;
            return this;
        }

        public Builder withTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder withFecha(LocalDateTime fecha) {
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
            this.menus = menus;
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
                ", restauranteId=" + restaurante.getId() + '\'' +
                ", usuarioId=" + usuario.getId() + '\'' +
                '}';
    }

    public String costoTotalAsString() {
        BigDecimal total = BigDecimal.ZERO;
        for (Menu menu : menus) {
            total = total.add(menu.getCosto());
        }
        return total.toString();
    }
}
