package com.safira.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by francisco on 02/03/15.
 */
@Entity
@Table(name = "retauranteslogin", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class RestauranteLogin implements Serializable {

    private int id;
    private String usuario;
    private byte[] hash;
    private byte[] salt;
    private Restaurante restaurante;

    public RestauranteLogin() {
    }

    public RestauranteLogin(Builder builder) {
        this.usuario = builder.userName;
        this.hash = builder.hash;
        this.salt = builder.salt;
        this.restaurante = builder.restaurante;
    }

    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "restaurante"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "RestauranteId", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "usuario", nullable = false, length = 30)
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Column(name = "password", nullable = false, length = 30)
    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @Column(name = "salt", nullable = false, length = 30)
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public static class Builder {

        private String userName;
        private byte[] hash;
        private byte[] salt;
        private Restaurante restaurante;

        public Builder withUserName(String userName) {
            this.userName = userName;
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

    public String getIdAsAtring() {
        return String.valueOf(id);
    }

    public String getHashAsString() {
        return new String(hash);
    }

    public String getSaltAsString() {
        return new String(salt);
    }
}
