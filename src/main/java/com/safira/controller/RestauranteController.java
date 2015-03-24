package com.safira.controller;

import com.safira.api.CreateRestauranteRequest;
import com.safira.api.LoginRestauranteRequest;
import com.safira.domain.Restaurantes;
import com.safira.domain.entities.Restaurante;
import com.safira.service.interfaces.RestauranteService;
import com.safira.service.implementation.RestauranteServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class RestauranteController {

    final RestauranteService restauranteService = new RestauranteServiceImpl();

    final static Logger restauranteLogger = Logger.getLogger("restauranteLogger");
    final static Logger restauranteWarnLogger = Logger.getLogger("restauranteWarnLogger");
    final static Logger restauranteErrorLogger = Logger.getLogger("restauranteErrorLogger");

    @RequestMapping(value = "/registerRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> registerRestaurante(@RequestBody CreateRestauranteRequest createRestauranteRequest) {
        Restaurante restaurante;
        try {
            restaurante = restauranteService.createRestaurante(createRestauranteRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> loginRestaurante(@RequestBody LoginRestauranteRequest loginRestauranteRequest) {
        Restaurante restaurante;
        try {
            restaurante = restauranteService.loginRestaurante(loginRestauranteRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestaurantes", method = RequestMethod.GET)
    public ResponseEntity<Object> getRestaurantes() {
        Restaurantes restaurantes;
        try {
            restaurantes = restauranteService.getAllRestaurantes();
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestauranteByUuid", method = RequestMethod.GET)
    public ResponseEntity<Object> getRestauranteById(@RequestParam(value = "uuid", required = true, defaultValue = "0") String uuid) {
        Restaurante restaurante;
        try {
            restaurante = restauranteService.getRestauranteByUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }
}
