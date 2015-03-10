package com.safira.controller;

import com.safira.common.ErrorObject;
import com.safira.domain.Restaurantes;
import com.safira.domain.SerializedObjectReciever;
import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.Hibernate.QueryService;
import com.safira.service.Log.RestauranteXMLWriter;
import com.safira.service.PasswordService;
import com.safira.service.RestauranteDeserializer;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicated to serving json RESTful webservice for Restaurantes
 */
@RestController
public class RestaurantesController {

    final static Logger restauranteLogger = Logger.getLogger("restauranteLogger");
    final static Logger restauranteWarnLogger = Logger.getLogger("restauranteWarnLogger");
    final static Logger restauranteErrorLogger = Logger.getLogger("restauranteErrorLogger");

    @RequestMapping(value = "/registerNewRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> post(@RequestBody SerializedObjectReciever serializedObjectReciever) {
        String serializedRestaurante = serializedObjectReciever.getSerializedObject();
        Restaurante restaurante = null;
        RestauranteLogin restauranteLogin = null;
        RestauranteDeserializer restauranteDeserializer = new RestauranteDeserializer(serializedRestaurante);
        try {
            restaurante = restauranteDeserializer.getRestaurante();
            restauranteLogin = restauranteDeserializer.getRestauranteLogin();
            QueryService queryService = new QueryService();
            queryService.insertObject(restaurante);
            queryService.insertObject(restauranteLogin);
            HibernateSessionService.shutDown();
        } catch (HibernateException e) {
            restauranteErrorLogger.error("An error occured when registering a new Restaurante!", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        restauranteLogger.info("Successfully registered Restaurante: \n"
                + RestauranteXMLWriter.createDocument(restaurante, restauranteLogin));
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody SerializedObjectReciever serializedObjectReciever) {
        String userNameAndPassword = serializedObjectReciever.getSerializedObject();
        String username = userNameAndPassword.split(":")[0];
        char[] password = userNameAndPassword.split(":")[1].toCharArray();
        Restaurante restaurante = null;
        try {
            QueryService queryService = new QueryService();
            RestauranteLogin restauranteLogin = queryService.getRestauranteLoginByUsuario(username);
            if (PasswordService.isExpectedPassword(password, restauranteLogin.getSalt(), restauranteLogin.getHash())) {
                restaurante = queryService.getRestauranteById(restauranteLogin.getId());
                restauranteLogger.info("Successful login to Restaurante = " + restaurante.getNombre() +
                        " with usuario = " + username + ".");
                return new ResponseEntity<>(restaurante, HttpStatus.OK);
            } else {
                restauranteLogger.info("Failed attemp to login to Restaurante = " + restaurante.getNombre() + ".");
                return new ResponseEntity<>(restaurante, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            restauranteErrorLogger.error("An error occured when retrieving Restaurante" +
                    " with usuario = " + username + ".", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getRestaurantes", method = RequestMethod.GET)
    public ResponseEntity<Object> get() {
        Restaurantes restaurantes = null;
        try {
            QueryService queryService = new QueryService();
            restaurantes = new Restaurantes(queryService.getRestaurantes());
            HibernateSessionService.shutDown();
            if (restaurantes.getRestaurantes().isEmpty()) {
                restauranteWarnLogger.warn("No Restaurantes found.");
                return new ResponseEntity<>(restaurantes, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            restauranteErrorLogger.error("An error occured when retrieving all Restaurantes!", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestauranteById", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        Restaurante restaurante = null;
        try {
            QueryService queryService = new QueryService();
            restaurante = queryService.getRestauranteById(restauranteId);
            HibernateSessionService.shutDown();
        } catch (HibernateException e) {
            restauranteErrorLogger.error("An error occured when retrieving Restaurante with id = " + id + "!", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            restauranteWarnLogger.warn("No Restaurante found with id = " + id + ".");
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }
}