package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @JsonManagedReference
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
        this(UUID.randomUUID().toString());
    }

    public RestauranteLogin(String uuid) {
        super(uuid);
    }

    public RestauranteLogin(Builder builder) {
        super(builder.uuid);
        this.usuario = builder.usuario;
        this.hash = builder.hash;
        this.salt = builder.salt;
        this.isVerified = builder.isVerified;
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

    public byte[] getSalt() {
        return salt;
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
        private String uuid;

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

        public Builder withUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public RestauranteLogin build() {
            return new RestauranteLogin(this);
        }
    }
}
