package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "restaurantes_login")
public class RestauranteLogin implements Serializable {

    private int id;
    private String usuario;
    private byte[] hash;
    private byte[] salt;
    private Restaurante restaurante;

    public RestauranteLogin() {
    }

    public RestauranteLogin(Builder builder) {
        this.usuario = builder.usuario;
        this.hash = builder.hash;
        this.salt = builder.salt;
        this.restaurante = builder.restaurante;
        if (restaurante != null && restaurante.getRestauranteLogin() != this) {
            restaurante.setRestauranteLogin(this);
        }
    }

    @Id
    @Column(name = "restaurante_id", unique = true, nullable = false)
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "restaurante"))
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "usuario", nullable = false, length = 50, unique = true)
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @JsonIgnore
    @Column(name = "hash", nullable = false)
    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @JsonIgnore
    @Column(name = "salt", nullable = false)
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (restaurante.getRestauranteLogin() != this) {
            restaurante.setRestauranteLogin(this);
        }
    }

    public static class Builder {

        private String usuario;
        private byte[] hash;
        private byte[] salt;
        private Restaurante restaurante;

        public Builder withUsuario(String usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder withHashedAndSaltedPassword(byte[] hash) {
            this.hash = hash;
            return this;
        }

        public Builder withSalt(byte[] salt) {
            this.salt = salt;
            return this;
        }

        public Builder withRestaurante(Restaurante restaurante) {
            this.restaurante = restaurante;
            return this;
        }

        public RestauranteLogin build() {
            return new RestauranteLogin(this);
        }

    }

    @Override
    public String toString() {
        return "RestauranteLogin{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", hash='" + hash + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
