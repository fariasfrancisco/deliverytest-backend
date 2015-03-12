package com.safira.controller;

import com.safira.common.DeserializerException;
import com.safira.common.ErrorObject;
import com.safira.domain.Restaurantes;
import com.safira.domain.SerializedObject;
import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;
import com.safira.service.PasswordService;
import com.safira.service.deserialize.RestauranteDeserializer;
import com.safira.service.hibernate.HibernateSessionService;
import com.safira.service.hibernate.QueryService;
import com.safira.service.log.RestauranteXMLWriter;
import org.apache.log4j.Logger;
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

    @RequestMapping(value = "/registerRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> registerRestaurante(@RequestBody SerializedObject serializedObject) {
        Restaurante restaurante;
        RestauranteLogin restauranteLogin;
        try {
            String serializedRestaurante = serializedObject.getSerializedObject();
            RestauranteDeserializer restauranteDeserializer = new RestauranteDeserializer(serializedRestaurante);
            restaurante = restauranteDeserializer.getRestaurante();
            restauranteLogin = restauranteDeserializer.getRestauranteLogin();
        } catch (DeserializerException e) {
            restauranteErrorLogger.error("An error occured when deserializing recieved String", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            QueryService queryService = new QueryService();
            queryService.insertObject(restaurante);
            queryService.insertObject(restauranteLogin);
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when registering a new Restaurante", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        restauranteLogger.info("Successfully registered Restaurante: \n"
                + RestauranteXMLWriter.createDocument(restaurante, restauranteLogin).asXML());
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> loginRestaurante(@RequestBody SerializedObject serializedObject) {
        String userNameAndPassword = serializedObject.getSerializedObject();
        String username = userNameAndPassword.split(":")[0];
        char[] password = userNameAndPassword.split(":")[1].toCharArray();
        Restaurante restaurante;
        try {
            QueryService queryService = new QueryService();
            RestauranteLogin restauranteLogin = queryService.getRestauranteLoginByUsuario(username);
            if (!PasswordService.isExpectedPassword(password, restauranteLogin.getSalt(), restauranteLogin.getHash())) {
                restauranteLogger.info("Failed attemp to log in with Usuario = " + username);
                return new ResponseEntity<>(new ErrorObject("failed attempt to log in"), HttpStatus.NOT_FOUND);
            }
            restaurante = queryService.getRestauranteById(restauranteLogin.getId());
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when retrieving Restaurante" +
                    " with usuario = " + username, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        restauranteLogger.info("Successful login to Restaurante = " + restaurante.getNombre() +
                " with usuario = " + username);
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestaurantes", method = RequestMethod.GET)
    public ResponseEntity<Object> getRestaurantes() {
        Restaurantes restaurantes;
        try {
            QueryService queryService = new QueryService();
            restaurantes = new Restaurantes(queryService.getRestaurantes());
            if (restaurantes.getRestaurantes().isEmpty()) {
                restauranteWarnLogger.warn("No Restaurantes found.");
                return new ResponseEntity<>(restaurantes, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when retrieving all Restaurantes", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestauranteById", method = RequestMethod.GET)
    public ResponseEntity<Object> getRestauranteById(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        Restaurante restaurante;
        try {
            QueryService queryService = new QueryService();
            restaurante = queryService.getRestauranteById(restauranteId);
        } catch (IndexOutOfBoundsException e) {
            restauranteWarnLogger.warn("No Restaurante found with id = " + id);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when retrieving Restaurante with id = " + id, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }
}