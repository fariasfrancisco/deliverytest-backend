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
    private String mail;
    private Set<Menu> menus = new HashSet<>(0);

    public Restaurante(Builder builder) {
        this.nombre = builder.nombre;
        this.direccion = builder.direccion;
        this.telefono = builder.telefono;
        this.mail = builder.mail;
        this.menus = builder.menus;
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

    @Column(name = "mail", nullable = false, length = 30)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public static class Builder {

        private String nombre;
        private String direccion;
        private String telefono;
        private String mail;
        private Set<Menu> menus = new HashSet<>();

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

        public Builder withMail(String mail) {
            this.mail = mail;
            return this;
        }

        public Builder withMenus(Set<Menu> menus) {
            this.menus = menus;
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
                ", mail='" + mail + '\'' +
                '}';
    }
}
