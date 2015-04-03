package com.safira.common;

/**
 * Created by francisco on 03/04/15.
 */
public class URLs {

    //RESTAURANTES
    public static final String GET_RESTAURANTES = "/getRestaurantes";
    public static final String GET_RESTAURANTE_BY_UUID = "/getRestauranteByUuid";
    public static final String REGISTER_RESTAURANTE = "/registerRestaurante";
    public static final String LOGIN_RESTAURANTE = "/loginRestaurante";

    //MENUS
    public static final String REGISTER_MENU = "/registerMenu";
    public static final String GET_MENU_BY_UUID = "/getMenuByUuid";
    public static final String GET_MENUS_BY_RESTAURANTE = "/getMenusByRestaurante";
    public static final String GET_MENUS_BY_PEDIDO = "/getMenusByPedido";

    //USUARIOS
    public static final String REGISTER_USUARIO = "/registerUsuario";
    public static final String LOGIN_USUARIO = "/loginUsuario";

    //PEDIDOS
    public static final String REGISTER_PEDIDO = "/registerUsuario";
    public static final String GET_PEDIDO_BY_UUID = "/getPedidoByUuid";
    public static final String GET_PEDIDOS_BY_RESTAURANTE = "/getPedidosByRestaurante";
    public static final String GET_PEDIDOS_BY_USUARIO = "/getPedidosByUsuario";
    public static final String GET_PEDIDOS_BY_USUARIO_AND_BY_RESTAURANTE = "/getPedidosByRestauranteAndUsuario";
}
