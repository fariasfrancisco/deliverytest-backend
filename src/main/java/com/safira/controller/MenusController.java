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
 * Created by Francisco on 26/02/2015.
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
        Menus menus = new Menus(queryService.GetMenuForRestaurante(restauranteId));
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
}
