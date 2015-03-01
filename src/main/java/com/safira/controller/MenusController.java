package com.safira.controller;

import com.safira.domain.Menus;
import com.safira.service.HibernateSessionService;
import com.safira.service.QueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * Controller dedicated to serving json RESTful webservice for Menus
 */
public class MenusController {

    @RequestMapping(value = "/getMenusByRestaurante", method = RequestMethod.GET)
    public Menus menusByRestaurante(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        QueryService queryService = new QueryService();
        Menus menus = new Menus(queryService.GetMenuByRestauranteId(restauranteId));
        HibernateSessionService.shutDown();
        return menus;
    }

    @RequestMapping(value = "/getMenusByPedido", method = RequestMethod.GET)
    public Menus menusByPedido(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int pedidoId;
        try {
            pedidoId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            pedidoId = 0;
        }
        QueryService queryService = new QueryService();
        Menus menus = new Menus(queryService.GetMenusForPedido(pedidoId));
        HibernateSessionService.shutDown();
        return menus;
    }

    /*
    @RequestMapping(value = "/insertMenu", method = RequestMethod.GET)
    public Menus menu() {
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurantes());
        Menu menu = new Menu.Builder()
                .withNombre("lomito especial")
                .withDescripcion("pan lomo queso jamon mayonesa etc.")
                .withCosto(new BigDecimal(52.00))
                .withRestaurante(restaurantes.getRestaurantes().get(0))
                .build();
        queryService = new QueryService();
        queryService.InsertObject(menu);
        queryService = new QueryService();
        Menus menus = new Menus(queryService.GetMenuByRestauranteId(restaurantes.getRestaurantes().get(0).getId()));
        HibernateSessionService.shutDown();
        return menus;
    }
    */

}
