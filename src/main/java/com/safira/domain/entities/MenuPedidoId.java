package com.safira.domain.entities;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by francisco on 03/04/15.
 */
@Embeddable
public class MenuPedidoId implements Serializable {

    @ManyToOne
    private Menu menu;

    @ManyToOne
    private Pedido pedido;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuPedidoId that = (MenuPedidoId) o;

        if (!menu.equals(that.menu)) return false;
        return pedido.equals(that.pedido);

    }

    @Override
    public int hashCode() {
        int result = menu.hashCode();
        result = 31 * result + pedido.hashCode();
        return result;
    }
}
