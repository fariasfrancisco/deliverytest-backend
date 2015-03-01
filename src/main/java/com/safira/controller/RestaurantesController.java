package com.safira.controller;

import com.safira.domain.Restaurantes;
import com.safira.entities.Restaurante;
import com.safira.service.HibernateSessionService;
import com.safira.service.QueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller dedicated to serving json RESTful webservice for Restaurantes
 */
@RestController
public class RestaurantesController {

    @RequestMapping(value = "/getRestaurantes", method = RequestMethod.GET)
    public Restaurantes restaurantes() {
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurantes());
        HibernateSessionService.shutDown();
        return restaurantes;
    }

    @RequestMapping(value = "/getRestauranteById", method = RequestMethod.GET)
    public Restaurante restaurante(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurante(restauranteId));
        Restaurante restaurante = restaurantes.get(0);
        HibernateSessionService.shutDown();
        return restaurante;
    }

    /*
    @RequestMapping(value = "/insertRestaurante", method = RequestMethod.GET)
    public Restaurantes restaurante() {
        Restaurante restaurante = new Restaurante.Builder()
                .withNombre("Super Mario2")
                .withDireccion("TENte Ibanez 123")
                .withTelefono("12341231114")
                .withEmail("email@email.com")
                .build();
        QueryService queryService = new QueryService();
        queryService.InsertObject(restaurante);
        HibernateSessionService.shutDown();
        queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurantes());
        HibernateSessionService.shutDown();
        return restaurantes;
    }
    */
}
