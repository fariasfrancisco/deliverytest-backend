package com.safira.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by francisco on 25/03/15.
 */
@Entity
public class RestauranteSessionToken extends AbstractPersistable<Long> {
    private static final long DEFAULT_LIFE_SPAN_IN_HOURS = 24 * 30; //30 days

    @Column(length = 36)
    private String token;

    private LocalDateTime creationTime;

    private LocalDateTime expirationTime;

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private RestauranteLogin restauranteLogin;

    public RestauranteSessionToken() {
    }

    public RestauranteSessionToken(RestauranteLogin restauranteLogin) {
        this(restauranteLogin, DEFAULT_LIFE_SPAN_IN_HOURS);
    }

    public RestauranteSessionToken(RestauranteLogin restauranteLogin, long lifeSpanInHours) {
        this.token = UUID.randomUUID().toString();
        this.restauranteLogin = restauranteLogin;
        this.creationTime = LocalDateTime.now();
        this.expirationTime = LocalDateTime.now().plusHours(lifeSpanInHours);
    }

    public boolean hasExpired() {
        return this.expirationTime != null && this.expirationTime.isBefore(LocalDateTime.now());
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }

    public void setRestauranteLogin(RestauranteLogin restauranteLogin) {
        this.restauranteLogin = restauranteLogin;
    }
}
