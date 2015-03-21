package com.safira.domain;

import com.safira.domain.entities.Restaurante;

import java.util.List;

/**
 * Class intended to simplify List storage of Restaurante objects and ease jSon transmision.
 */
public class Restaurantes {
    private List<Restaurante> restaurantes;

    public Restaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public Restaurante get(int i) {
        return restaurantes.get(i);
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
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
