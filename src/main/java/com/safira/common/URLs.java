package com.safira.common;

/**
 * Created by francisco on 03/04/15.
 */
public class URLs {

    //OPERATORS
    public static final String PAGINATION = "/{pageNumber}";
    private static final String GETALL = "/getAll";
    private static final String GET = "/get";
    private static final String POST = "/post";
    private static final String LOGIN = "/login";
    private static final String VERIFY = "/login";

    //RESTAURANTES
    private static final String RESTAURANTE = "/restaurante";
    public static final String GET_RESTAURANTES = RESTAURANTE + GETALL;
    public static final String REGISTER_RESTAURANTE = RESTAURANTE + POST;
    public static final String GET_RESTAURANTE_BY_UUID = RESTAURANTE + GET;
    public static final String GET_RESTAURANTES_BY_NOMBRE = RESTAURANTE + GET;
    public static final String LOGIN_RESTAURANTE = RESTAURANTE + LOGIN;
    public static final String VERIFY_TOKEN = RESTAURANTE + VERIFY;

    //MENUS
    private static final String MENU = "/menu";
    public static final String REGISTER_MENU = MENU + POST;
    public static final String GET_MENU_BY_UUID = MENU + GET;
    public static final String GET_MENUS_BY_RESTAURANTE = MENU + GET;
    public static final String GET_MENUS_BY_PEDIDO = MENU + GET;

    //USUARIOS
    private static final String USUARIO = "/usuario";
    public static final String REGISTER_USUARIO = USUARIO + POST;
    public static final String GET_USUARIO = USUARIO + GET;

    //DIRECCION
    private static final String DIRECCION = "/direccion";
    public static final String REGISTER_DIRECCION = DIRECCION + POST;

    //PEDIDOS
    private static final String PEDIDO = "/pedido";
    public static final String REGISTER_PEDIDO = PEDIDO + POST;
    public static final String GET_PEDIDO_BY_UUID = PEDIDO + GET;
    public static final String GET_PEDIDOS_BY_RESTAURANTE = PEDIDO + GET;
    public static final String GET_PEDIDOS_BY_USUARIO = PEDIDO + GET;
    public static final String GET_PEDIDOS_BY_USUARIO_AND_BY_RESTAURANTE = PEDIDO + GET;
}
