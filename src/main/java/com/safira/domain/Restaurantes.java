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

    public Restaurante get(int i) {
        return restaurantes.get(i);
    }

    @Override
    public String toString() {
        String s = "Restaurantes{";
        for (Restaurante r : restaurantes) {
            s += r.toString();
        }
        s += '}';
        return s;
    }
}
