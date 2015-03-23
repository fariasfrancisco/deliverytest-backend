package com.safira.domain;

import com.safira.domain.entities.Pedido;

import java.util.List;

/**
 * Class intended to simplify List storage of Pedido objects and ease jSon transmision.
 */
public class Pedidos {
    private List<Pedido> pedidos;

    public Pedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Pedido get(int i) {
        return pedidos.get(i);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public String toString() {
        String s = "Pedidos{";
        for (Pedido p : pedidos) {
            s += p.toString();
        }
        s += '}';
        return s;
    }
}
