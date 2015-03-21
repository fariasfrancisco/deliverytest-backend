package com.safira.controller;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.LoginException;
import com.safira.domain.Restaurantes;
import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import com.safira.domain.repositories.RestauranteLoginRepository;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.service.PasswordService;
import com.safira.service.deserialize.RestauranteDeserializer;
import com.safira.service.log.RestauranteXMLWriter;
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
        try {
            String serializedRestaurante = serializedObject.getSerializedObject();
            RestauranteDeserializer restauranteDeserializer = new RestauranteDeserializer(serializedRestaurante);
            restaurante = restauranteDeserializer.getRestaurante();
            restauranteLogin = restauranteDeserializer.getRestauranteLogin();
        } catch (DeserializerException e) {
            restauranteErrorLogger.error("An error occured when deserializing recieved String", e);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        try {
            restauranteRepository.save(restaurante);
            restauranteLoginRepository.save(restauranteLogin);
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when registering a new Restaurante", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        restauranteLogger.info("Successfully registered Restaurante: \n"
                + RestauranteXMLWriter.createDocument(restaurante, restauranteLogin).asXML());
        return new ResponseEntity<>(restaurante, HttpStatus.OK);

    }

    @RequestMapping(value = "/loginRestaurante", method = RequestMethod.POST)
    public ResponseEntity<Object> loginRestaurante(@RequestBody SerializedObject serializedObject) {
        String[] userNameAndPassword = serializedObject.getSerializedObject().split(";");
        String username = userNameAndPassword[0];
        char[] password = userNameAndPassword[1].toCharArray();
        Restaurante restaurante;
        try {
            RestauranteLogin restauranteLogin = restauranteLoginRepository.findByUsuario(username);
            if (!PasswordService.isExpectedPassword(password, restauranteLogin.getSalt(), restauranteLogin.getHash())) {
                restauranteLogger.info("Failed attemp to log in with Usuario = " + username);
                return new ResponseEntity<>(new LoginException(), HttpStatus.NOT_FOUND);
            }
            restaurante = restauranteRepository.findByUuid(restauranteLogin.getIdentifier());
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when retrieving Restaurante" +
                    " with usuario = " + username, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        restauranteLogger.info("Successful login to Restaurante = " + restaurante.getNombre() +
                " with usuario = " + username);
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRestaurantes", method = RequestMethod.GET)
    public ResponseEntity<Object> getRestaurantes() {
        Restaurantes restaurantes;
        try {
            restaurantes = new Restaurantes(restauranteRepository.findAll());
            if (restaurantes.getRestaurantes().isEmpty()) {
                restauranteWarnLogger.warn("No Restaurantes found.");
                return new ResponseEntity<>(restaurantes, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when retrieving all Restaurantes", e);
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
            restauranteWarnLogger.warn("No Restaurante found with uuid = " + uuid);
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            restauranteErrorLogger.error("An error occured when retrieving Restaurante with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }
}
