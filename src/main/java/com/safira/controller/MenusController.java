package com.safira.controller;

import com.safira.domain.Menus;
import com.safira.service.HibernateSessionService;
import com.safira.service.QueryService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * Controller dedicated to serving json RESTful webservice for Menus
 */
public class MenusController {

    private static final Logger logger = Logger.getLogger(MenusController.class);

    @RequestMapping(value = "/getMenusByRestaurante", method = RequestMethod.GET)
    public Menus menusByRestaurante(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        Menus menus = null;
        try {
            QueryService queryService = new QueryService();
            menus = new Menus(queryService.GetMenuByRestauranteId(restauranteId));
            HibernateSessionService.shutDown();
        } catch (HibernateException e) {
            logger.error("Hibernate Exception when retrieving menus with Restaurante Id = " + restauranteId);
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
        }
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
