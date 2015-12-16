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
    private static final String VERIFY = "/verify";
    private static final String GETBYUUID = GET + "ByUUID";
    private static final String GETBYNAME = GET + "ByName";
    private static final String GETBYRESTAURANTE = GET + "ByRestaurante";
    private static final String GETBYPEDIDO = GET + "ByPedido";
    private static final String GETBYUSUARIO = GET + "ByUsuario";

    //RESTAURANTES
    private static final String RESTAURANTE = "/restaurante";
    public static final String GET_RESTAURANTES = RESTAURANTE + GETALL;
    public static final String REGISTER_RESTAURANTE = RESTAURANTE + POST;
    public static final String GET_RESTAURANTE_BY_UUID = RESTAURANTE + GETBYUUID;
    public static final String GET_RESTAURANTES_BY_NOMBRE = RESTAURANTE + GETBYNAME;
    public static final String LOGIN_RESTAURANTE = RESTAURANTE + LOGIN;
    public static final String VERIFY_TOKEN = RESTAURANTE + VERIFY;

    //MENUS
    private static final String MENU = "/menu";
    public static final String REGISTER_MENU = MENU + POST;
    public static final String GET_MENU_BY_UUID = MENU + GETBYUUID;
    public static final String GET_MENUS_BY_RESTAURANTE = MENU + GETBYRESTAURANTE;
    public static final String GET_MENUS_BY_PEDIDO = MENU + GETBYPEDIDO;

    //USUARIOS
    private static final String USUARIO = "/usuario";
    public static final String REGISTER_USUARIO = USUARIO + POST;
    public static final String GET_USUARIO = USUARIO + GETBYUUID;

    //DIRECCION
    private static final String DIRECCION = "/direccion";
    public static final String REGISTER_DIRECCION = DIRECCION + POST;

    //PEDIDOS
    private static final String PEDIDO = "/pedido";
    public static final String REGISTER_PEDIDO = PEDIDO + POST;
    public static final String GET_PEDIDO_BY_UUID = PEDIDO + GETBYUUID;
    public static final String GET_PEDIDOS_BY_RESTAURANTE = PEDIDO + GETBYRESTAURANTE;
    public static final String GET_PEDIDOS_BY_USUARIO = PEDIDO + GETBYUSUARIO;
    public static final String GET_PEDIDOS_BY_USUARIO_AND_BY_RESTAURANTE = PEDIDO + GETBYUSUARIO + "AndRestaurante";
}
