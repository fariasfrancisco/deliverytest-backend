package com.safira.api;

import java.util.UUID;

/**
 * Created by francisco on 25/03/15.
 */
public class AuthenticatedRestauranteToken {

    private String restauranteUuid;
    private String token;

    public AuthenticatedRestauranteToken() {
    }

    public AuthenticatedRestauranteToken(String restauranteUuid) {
        this.restauranteUuid = restauranteUuid;
        this.token = UUID.randomUUID().toString();
    }

    public AuthenticatedRestauranteToken(String restauranteUuid, String token) {
        this.restauranteUuid = restauranteUuid;
        this.token = token;
    }

    public String getRestauranteUuid() {
        return restauranteUuid;
    }

    public void setRestauranteUuid(String restauranteUuid) {
        this.restauranteUuid = restauranteUuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
