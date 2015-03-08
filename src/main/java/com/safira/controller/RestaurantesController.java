package com.safira.controller;

import com.safira.domain.RestauranteLogins;
import com.safira.domain.Restaurantes;
import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;
import com.safira.service.HibernateSessionService;
import com.safira.service.PasswordService;
import com.safira.service.QueryService;
import com.safira.service.RestauranteDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicated to serving json RESTful webservice for Restaurantes
 */
@RestController
public class RestaurantesController {

    @RequestMapping(value = "/getRestaurantes", method = RequestMethod.GET)
    public ResponseEntity<Restaurantes> get() {
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestaurantes());
        HibernateSessionService.shutDown();
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestauranteById", method = RequestMethod.GET)
    public ResponseEntity<Restaurante> get(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        QueryService queryService = new QueryService();
        Restaurantes restaurantes = new Restaurantes(queryService.GetRestauranteById(restauranteId));
        Restaurante restaurante = restaurantes.get(0);
        HibernateSessionService.shutDown();
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Restaurante> login(@RequestBody String userNameAndPassword) {
        String username = userNameAndPassword.split(":")[0];
        char[] password = userNameAndPassword.split(":")[1].toCharArray();
        Restaurante restaurante = null;
        try {
            QueryService queryService = new QueryService();
            RestauranteLogins restauranteLogins = new RestauranteLogins(queryService.GetRestauranteLoginByUsuario(username));
            RestauranteLogin restauranteLogin = restauranteLogins.get(0);
            if (PasswordService.isExpectedPassword(password, restauranteLogin.getSalt(), restauranteLogin.getHash())) {
                Restaurantes restaurantes = new Restaurantes(queryService.GetRestauranteById(restauranteLogin.getId()));
                restaurante = restaurantes.get(0);
                return new ResponseEntity<>(restaurante, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(restaurante, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(restaurante, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/registerNewRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Restaurante> post(@RequestBody String serializedRestaurante) {
        Restaurante restaurante = null;
        try {
            RestauranteDeserializer restauranteDeserializer = new RestauranteDeserializer(serializedRestaurante);
            QueryService queryService = new QueryService();
            queryService.InsertObject(restauranteDeserializer.getRestaurante());
            queryService.InsertObject(restauranteDeserializer.getRestauranteLogin());
            HibernateSessionService.shutDown();
            restaurante = restauranteDeserializer.getRestaurante();
        } catch (Exception e) {
            return new ResponseEntity<>(restaurante, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante,HttpStatus.OK);
    }


}
