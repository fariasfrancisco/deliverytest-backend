package com.safira.controller;

import com.safira.domain.Restaurantes;
import com.safira.service.HibernateSessionService;
import com.safira.service.QueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * Created by Francisco on 26/02/2015.
 */
public class RestaurantesController {

    @RequestMapping("/restaurantes")
    public Restaurantes restaurantes() {
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurants());
        HibernateSessionService.shutDown();
        return restaurantes;
    }

}
