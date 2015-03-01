package com.safira.domain;

import com.safira.entities.Pedido;

import java.util.List;

/**
 * Created by Francisco on 26/02/2015.
 */
public class Pedidos {
    private List<Pedido> pedidos;

    public Pedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public String toString() {
        String s = "Pedidos{pedido=";
        for (Pedido p : pedidos) {
            s += p.toString();
        }
        s += '}';
        return s;
    }
}
