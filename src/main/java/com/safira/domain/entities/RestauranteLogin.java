package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safira.domain.entity.ModelEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class RestauranteLogin extends ModelEntity {

    @Column(nullable = false, unique = true)
    private String usuario;
    private byte[] hash;
    private byte[] salt;
    private boolean isVerified;

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Restaurante restaurante;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restauranteLogin")
    private Set<RestauranteVerificationToken> restauranteVerificationTokens = new HashSet<>();

    @JsonManagedReference
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "restauranteLogin", cascade = CascadeType.ALL)
    private RestauranteSessionToken restauranteSessionToken;

    public RestauranteLogin() {
        this(UUID.randomUUID());
    }

    public RestauranteLogin(UUID uuid) {
        super(uuid);
    }

    public RestauranteLogin(Builder builder) {
        super(builder.uuid);
        this.usuario = builder.usuario;
        this.hash = builder.hash;
        this.salt = builder.salt;
        this.isVerified = builder.isVerified;
        this.restaurante = builder.restaurante;
        if (restaurante != null && restaurante.getRestauranteLogin() != this) {
            restaurante.setRestauranteLogin(this);
        }
        this.restauranteSessionToken = builder.restauranteSessionToken;
        if (restauranteSessionToken != null && restauranteSessionToken.getRestauranteLogin() != this) {
            restauranteSessionToken.setRestauranteLogin(this);
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

    public boolean isVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
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

    public Set<RestauranteVerificationToken> getRestauranteVerificationTokens() {
        return restauranteVerificationTokens;
    }

    public void setRestauranteVerificationTokens(Set<RestauranteVerificationToken> restauranteVerificationTokens) {
        this.restauranteVerificationTokens = restauranteVerificationTokens;
        for (RestauranteVerificationToken restauranteVerificationToken : restauranteVerificationTokens) {
            if (restauranteVerificationToken.getRestauranteLogin() != this)
                restauranteVerificationToken.setRestauranteLogin(this);
        }
    }

    public RestauranteSessionToken getRestauranteSessionToken() {
        return restauranteSessionToken;
    }

    public void setRestauranteSessionToken(RestauranteSessionToken restauranteSessionToken) {
        this.restauranteSessionToken = restauranteSessionToken;
        if (restauranteSessionToken.getRestauranteLogin() != this) restauranteSessionToken.setRestauranteLogin(this);
    }

    public static class Builder {

        private String usuario;
        private byte[] hash;
        private byte[] salt;
        private boolean isVerified = false;
        private UUID uuid;
        private Restaurante restaurante;
        private RestauranteSessionToken restauranteSessionToken;

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

        public Builder withIsVerified(boolean isVerified) {
            this.isVerified = isVerified;
            return this;
        }

        public Builder withUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder withRestaurante(Restaurante restaurante) {
            this.restaurante = restaurante;
            return this;
        }

        public Builder withRestauranteSessionToken(RestauranteSessionToken restauranteSessionToken) {
            this.restauranteSessionToken = restauranteSessionToken;
            return this;
        }

        public RestauranteLogin build() {
            return new RestauranteLogin(this);
        }
    }
}
