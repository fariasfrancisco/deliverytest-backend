package com.safira.controller;

import com.safira.api.AuthenticatedRestauranteToken;
import com.safira.api.CreateRestauranteRequest;
import com.safira.api.LoginRestauranteRequest;
import com.safira.domain.Restaurantes;
import com.safira.domain.entities.Restaurante;
import com.safira.service.interfaces.RestauranteService;
import com.safira.service.log.RestauranteXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.safira.common.URLs.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class RestauranteController {

    @Autowired
    RestauranteService restauranteService;

    final static Logger restauranteLogger = Logger.getLogger("restauranteLogger");
    final static Logger restauranteErrorLogger = Logger.getLogger("restauranteErrorLogger");

    @RequestMapping(value = REGISTER_RESTAURANTE, method = RequestMethod.POST)
    public ResponseEntity registerRestaurante(@RequestBody CreateRestauranteRequest createRestauranteRequest) {
        Restaurante restaurante;
        try {
            restaurante = restauranteService.createRestaurante(createRestauranteRequest);
            restauranteLogger.info("Successfully created new Restaurante: \n" +
                    RestauranteXMLWriter.createDocument(restaurante).getDocument());
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when creating a new Restaurante.", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @RequestMapping(value = LOGIN_RESTAURANTE, method = RequestMethod.POST)
    public ResponseEntity loginRestaurante(@RequestBody LoginRestauranteRequest loginRestauranteRequest) {
        AuthenticatedRestauranteToken authenticatedRestauranteToken;
        try {
            authenticatedRestauranteToken = restauranteService.loginRestaurante(loginRestauranteRequest);
            restauranteLogger.info("Successfully created logged Restaurante: " +
                    authenticatedRestauranteToken.getRestauranteUuid());
        } catch (Exception e) {
            restauranteErrorLogger.error("Failed attempt to log in Restaurante with usuario = "
                    + loginRestauranteRequest.getUsuario(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(authenticatedRestauranteToken, HttpStatus.OK);
    }

    @RequestMapping(value = GET_RESTAURANTES, method = RequestMethod.GET)
    public ResponseEntity getRestaurantes() {
        Restaurantes restaurantes;
        try {
            restaurantes = restauranteService.getAllRestaurantes();
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when finding all Restaurantes", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = GET_RESTAURANTE_BY_UUID, method = RequestMethod.GET)
    public ResponseEntity<Object> getRestauranteById(@RequestParam(value = "uuid", required = true, defaultValue = "0") String uuid) {
        Restaurante restaurante;
        try {
            restaurante = restauranteService.getRestauranteByUuid(uuid);
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when finding Restaurante with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }
}
