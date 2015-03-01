package com.safira.controller;

import com.safira.domain.Restaurantes;
import com.safira.service.HibernateSessionService;
import com.safira.service.QueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Francisco on 26/02/2015.
 */
@RestController
public class RestaurantesController {

    @RequestMapping(value = "/restaurantes", method = RequestMethod.GET)
    public Restaurantes restaurantes() {
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurantes());
        HibernateSessionService.shutDown();
        return restaurantes;
    }

    @RequestMapping(value = "/restaurantes", method = RequestMethod.GET)
    public Restaurantes restaurantes(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurante(restauranteId));
        HibernateSessionService.shutDown();
        return restaurantes;
    }

}
