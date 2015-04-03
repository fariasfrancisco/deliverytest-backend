package com.safira.domain.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by francisco on 03/04/15.
 */
@Entity
@Table(name = "menu_pedido")
@AssociationOverrides({
        @AssociationOverride(name = "pk.menu", joinColumns = @JoinColumn(name = "menu_id")),
        @AssociationOverride(name = "pk.pedido", joinColumns = @JoinColumn(name = "pedido_id"))})
public class MenuPedido implements Serializable {

    @EmbeddedId
    private MenuPedidoId pk = new MenuPedidoId();
    private BigDecimal cantidad;

    public MenuPedido() {
    }

    public MenuPedidoId getPk() {
        return pk;
    }

    public void setPk(MenuPedidoId pk) {
        this.pk = pk;
    }

    @Transient
    public Menu getMenu() {
        return pk.getMenu();
    }

    public void setMenu(Menu menu) {
        pk.setMenu(menu);
        if (!menu.getMenuPedidos().contains(this)) menu.getMenuPedidos().add(this);
    }

    @Transient
    public Pedido getPedido() {
        return pk.getPedido();
    }

    public void setPedido(Pedido pedido) {
        pk.setPedido(pedido);
        if (!pedido.getMenuPedidos().contains(this)) pedido.getMenuPedidos().add(this);
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuPedido that = (MenuPedido) o;

        if (!pk.equals(that.pk)) return false;
        return cantidad.equals(that.cantidad);

    }

    @Override
    public int hashCode() {
        int result = pk.hashCode();
        result = 31 * result + cantidad.hashCode();
        return result;
    }
}
