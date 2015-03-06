package com.safira.domain;

import com.safira.entities.RestauranteLogin;

import java.util.List;

/**
 * Created by francisco on 06/03/15.
 */
public class RestauranteLogins {
    private List<RestauranteLogin> restauranteLogins;

    public RestauranteLogins(List<RestauranteLogin> resrestauranteLoginstaurantes) {
        this.restauranteLogins = restauranteLogins;
    }

    public RestauranteLogin get(int i) {
        return restauranteLogins.get(i);
    }

    public List<RestauranteLogin> getRestauranteLogins() {
        return restauranteLogins;
    }

    @Override
    public String toString() {
        String s = "RestauranteLogins{";
        for (RestauranteLogin r : restauranteLogins) {
            s += r.toString();
        }
        s += '}';
        return s;
    }
}
