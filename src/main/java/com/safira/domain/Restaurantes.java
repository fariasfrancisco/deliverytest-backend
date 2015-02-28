package com.safira.domain;

import com.safira.entities.Restaurante;

import java.util.List;

/**
 * Created by Francisco on 26/02/2015.
 */
public class Restaurantes {
    private List<Restaurante> restaurantes;

    public Restaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    @Override
    public String toString() {
        String s = "Restaurantes{restaurantes=";
        for (Restaurante r : restaurantes) {
            s += r.toString();
        }
        s += '}';
        return s;
    }
}
