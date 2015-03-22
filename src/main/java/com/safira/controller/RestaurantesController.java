package com.safira.controller;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.LoginException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Restaurantes;
import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import com.safira.domain.repositories.RestauranteLoginRepository;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.service.deserialize.RestauranteDeserializer;
import com.safira.service.deserialize.RestauranteLoginDeserializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicated to serving json RESTful webservice for Restaurantes
 */
@RestController
public class RestaurantesController {

    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    RestauranteLoginRepository restauranteLoginRepository;

    final static Logger restauranteLogger = Logger.getLogger("restauranteLogger");
    final static Logger restauranteWarnLogger = Logger.getLogger("restauranteWarnLogger");
    final static Logger restauranteErrorLogger = Logger.getLogger("restauranteErrorLogger");

    @RequestMapping(value = "/registerRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> registerRestaurante(@RequestBody SerializedObject serializedObject) {
        Restaurante restaurante;
        RestauranteLogin restauranteLogin;
        String serializedRestaurante = serializedObject.getSerializedObject();
        try {
            RestauranteDeserializer restauranteDeserializer = new RestauranteDeserializer(serializedRestaurante);
            restaurante = restauranteDeserializer.getRestaurante();
            restauranteLogin = restauranteDeserializer.getRestauranteLogin();
        } catch (DeserializerException | ValidatorException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        try {
            restauranteLoginRepository.save(restauranteLogin);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> loginRestaurante(@RequestBody SerializedObject serializedObject) {
        Restaurante restaurante;
        String serializedRestauranteLogin = serializedObject.getSerializedObject();
        try {
            RestauranteLoginDeserializer restauranteLoginDeserializer =
                    new RestauranteLoginDeserializer(serializedRestauranteLogin, restauranteLoginRepository, restauranteRepository);
            restaurante = restauranteLoginDeserializer.getRestaurante();
        } catch (DeserializerException | JPAQueryException | LoginException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestaurantes", method = RequestMethod.GET)
    public ResponseEntity<Object> getRestaurantes() {
        Restaurantes restaurantes;
        try {
            restaurantes = new Restaurantes(restauranteRepository.findAll());
            if (restaurantes.getRestaurantes().isEmpty()) {
                return new ResponseEntity<>(restaurantes, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestauranteByUuid", method = RequestMethod.GET)
    public ResponseEntity<Object> getRestauranteById(@RequestParam(value = "uuid", required = true, defaultValue = "0") String uuid) {
        Restaurante restaurante;
        try {
            restaurante = restauranteRepository.findByUuid(uuid);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }
}
