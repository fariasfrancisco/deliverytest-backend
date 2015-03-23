package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class RestauranteLogin extends ModelEntity {

    @Column(nullable = false, unique = true)
    private String usuario;
    @JsonIgnore
    private byte[] hash;
    @JsonIgnore
    private byte[] salt;
    private boolean verificado;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Restaurante restaurante;

    public RestauranteLogin() {
        this(UUID.randomUUID());
    }

    public RestauranteLogin(UUID uuid) {
        super(UUID.randomUUID());
    }

    public RestauranteLogin(Builder builder) {
        super(UUID.randomUUID());
        this.usuario = builder.usuario;
        this.hash = builder.hash;
        this.salt = builder.salt;
        this.verificado = builder.verificado;
        this.restaurante = builder.restaurante;
        if (restaurante != null && restaurante.getRestauranteLogin() != this) {
            restaurante.setRestauranteLogin(this);
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

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
        private boolean verificado;
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

        public Builder withVerificado(boolean verificado) {
            this.verificado = verificado;
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
        final StringBuffer sb = new StringBuffer("RestauranteLogin{");
        sb.append("usuario='").append(usuario).append('\'');
        sb.append(", hash=");
        if (hash == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < hash.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(hash[i]);
            sb.append(']');
        }
        sb.append(", salt=");
        if (salt == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < salt.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(salt[i]);
            sb.append(']');
        }
        sb.append(", verificado=").append(verificado);
        sb.append('}');
        return sb.toString();
    }
}
