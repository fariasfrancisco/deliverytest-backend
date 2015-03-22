package com.safira.domain.entities;

import com.safira.domain.entity.ModelEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Pedido extends ModelEntity {

    private String calle;
    private String numero;
    private String piso;
    private String departamento;
    private String telefono;

    @Type(type = "com.safira.common.LocalDateTimeUserType")
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Menu_Pedido", joinColumns = {
            @JoinColumn(name = "pedido_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    })
    private Set<Menu> menus = new HashSet<>();

    public Pedido() {
        this(UUID.randomUUID());
    }

    public Pedido(UUID uuid) {
        super(UUID.randomUUID());
    }

    public Pedido(Builder builder) {
        super(UUID.randomUUID());
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

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (!usuario.getPedidos().contains(this)) {
            usuario.getPedidos().add(this);
        }
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (!restaurante.getPedidos().contains(this)) {
            restaurante.getPedidos().add(this);
        }
    }

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

    public String costoTotalAsString() {
        BigDecimal total = BigDecimal.ZERO;
        for (Menu menu : menus) {
            total = total.add(menu.getCosto());
        }
        return total.toString();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pedido{");
        sb.append("calle='").append(calle).append('\'');
        sb.append(", numero='").append(numero).append('\'');
        sb.append(", piso='").append(piso).append('\'');
        sb.append(", departamento='").append(departamento).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", fecha=").append(fecha);
        sb.append(", menus=").append(menus);
        sb.append('}');
        return sb.toString();
    }
}
