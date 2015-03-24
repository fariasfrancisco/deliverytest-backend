package com.safira.domain;

import com.safira.common.SafiraUtils;
import com.safira.domain.entities.Restaurante;

import java.util.List;

/**
 * Class intended to simplify List storage of Restaurante objects and ease jSon transmision.
 */
public class Restaurantes {
    private List<Restaurante> restaurantes;

    public Restaurantes(Iterable<Restaurante> restaurantes) {
        this.restaurantes = SafiraUtils.toList(restaurantes);
    }

    public Restaurante get(int i) {
        return restaurantes.get(i);
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }
}
