package com.safira.controller;

import com.safira.domain.Restaurantes;
import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.PasswordService;
import com.safira.service.Hibernate.QueryService;
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
        Restaurantes restaurantes = null;
        try {
            QueryService queryService = new QueryService();
            restaurantes = new Restaurantes(queryService.getRestaurantes());
            HibernateSessionService.shutDown();
        } catch (Exception e) {
            //TODO log stacktrace
            return new ResponseEntity<>(restaurantes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestauranteById", method = RequestMethod.GET)
    public ResponseEntity<Restaurante> get(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            //TODO log stacktrace
            restauranteId = 0;
        }
        Restaurante restaurante = null;
        try {
            QueryService queryService = new QueryService();
            restaurante = queryService.getRestauranteById(restauranteId);
            HibernateSessionService.shutDown();
        } catch (Exception e) {
            //TODO log stacktrace
            return new ResponseEntity<>(restaurante, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Restaurante> login(@RequestBody String userNameAndPassword) {
        String username = userNameAndPassword.split(":")[0];
        char[] password = userNameAndPassword.split(":")[1].toCharArray();
        Restaurante restaurante = null;
        try {
            QueryService queryService = new QueryService();
            RestauranteLogin restauranteLogin = queryService.getRestauranteLoginByUsuario(username);
            if (PasswordService.isExpectedPassword(password, restauranteLogin.getSalt(), restauranteLogin.getHash())) {
                restaurante = queryService.getRestauranteById(restauranteLogin.getId());
                //TODO log Restaurante's successful login
                return new ResponseEntity<>(restaurante, HttpStatus.OK);
            } else {
                //TODO log Restaurante's failed login
                return new ResponseEntity<>(restaurante, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //TODO log Restaurante's failed login & stacktrace
            return new ResponseEntity<>(restaurante, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/registerNewRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Restaurante> post(@RequestBody String serializedRestaurante) {
        Restaurante restaurante = null;
        try {
            RestauranteDeserializer restauranteDeserializer = new RestauranteDeserializer(serializedRestaurante);
            QueryService queryService = new QueryService();
            queryService.insertObject(restauranteDeserializer.getRestaurante());
            queryService.insertObject(restauranteDeserializer.getRestauranteLogin());
            HibernateSessionService.shutDown();
            restaurante = restauranteDeserializer.getRestaurante();
        } catch (Exception e) {
            //TODO log Restaurante's failed registration & stacktrace
            return new ResponseEntity<>(restaurante, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO log Restaurante's successful registration
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }


}
