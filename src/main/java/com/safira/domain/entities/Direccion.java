package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by francisco on 23/03/15.
 */
@Entity
public class Direccion extends ModelEntity {
    private String calle;
    private String numero;
    private String piso;
    private String departamento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "direccion")
    private Set<Pedido> pedidos = new HashSet<>();

    public Direccion() {
        this(UUID.randomUUID().toString());
    }

    public Direccion(String uuid) {
        super(uuid);
    }

    public Direccion(Builder builder) {
        super(UUID.randomUUID().toString());
        this.calle = builder.calle;
        this.numero = builder.numero;
        this.piso = builder.piso;
        this.departamento = builder.departamento;
        this.usuario = builder.usuario;
        if (!usuario.getDirecciones().contains(this)) usuario.getDirecciones().add(this);
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getPiso() {
        return piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (!usuario.getDirecciones().contains(this)) usuario.getDirecciones().add(this);
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        pedidos.stream()
                .filter(pedido -> pedido.getDireccion() != this)
                .forEach(pedido -> pedido.setDireccion(this));
    }

    public static class Builder {
        private String calle;
        private String numero;
        private String piso;
        private String departamento;
        private Usuario usuario;

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

        public Builder withUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Direccion build() {
            return new Direccion(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Direccion direccion = (Direccion) o;

        if (!calle.equals(direccion.calle)) return false;
        if (!numero.equals(direccion.numero)) return false;
        if (piso != null ? !piso.equals(direccion.piso) : direccion.piso != null) return false;
        return !(departamento != null ? !departamento.equals(direccion.departamento) : direccion.departamento != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + calle.hashCode();
        result = 31 * result + numero.hashCode();
        result = 31 * result + (piso != null ? piso.hashCode() : 0);
        result = 31 * result + (departamento != null ? departamento.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Direccion{");
        sb.append("usuario=").append(usuario);
        sb.append(", departamento='").append(departamento).append('\'');
        sb.append(", piso='").append(piso).append('\'');
        sb.append(", numero='").append(numero).append('\'');
        sb.append(", calle='").append(calle).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
