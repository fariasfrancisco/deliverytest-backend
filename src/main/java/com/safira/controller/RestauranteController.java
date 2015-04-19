package com.safira.controller;

import com.safira.api.AuthenticatedRestauranteToken;
import com.safira.api.CreateRestauranteRequest;
import com.safira.api.LoginRestauranteRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.LoginException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Restaurantes;
import com.safira.domain.entities.Restaurante;
import com.safira.service.Validator;
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

    private ErrorOutput errors = new ErrorOutput();

    final static Logger restauranteLogger = Logger.getLogger("restauranteLogger");
    final static Logger restauranteErrorLogger = Logger.getLogger("restauranteErrorLogger");

    @RequestMapping(value = REGISTER_RESTAURANTE, method = RequestMethod.POST)
    public ResponseEntity registerRestaurante(@RequestBody CreateRestauranteRequest createRestauranteRequest) {
        Restaurante restaurante;
        try {
            Validator.validateRestaurante(createRestauranteRequest, errors);
            restaurante = restauranteService.createRestaurante(createRestauranteRequest);
            restauranteLogger.info("Successfully created new Restaurante: \n" +
                    RestauranteXMLWriter.createDocument(restaurante).asXML());
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when creating a new Restaurante.", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.CREATED);
    }

    @RequestMapping(value = LOGIN_RESTAURANTE, method = RequestMethod.POST)
    public ResponseEntity loginRestaurante(@RequestBody LoginRestauranteRequest loginRestauranteRequest) {
        AuthenticatedRestauranteToken authenticatedRestauranteToken;
        try {
            Validator.validateRestauranteLogin(loginRestauranteRequest, errors);
            authenticatedRestauranteToken = restauranteService.loginRestaurante(loginRestauranteRequest, errors);
            restauranteLogger.info("Successfully created logged Restaurante: " +
                    authenticatedRestauranteToken.getRestauranteUuid());
        } catch (EmptyQueryResultException | ValidatorException | LoginException e) {
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            restauranteErrorLogger.error("Failed attempt to log in Restaurante with usuario = "
                    + loginRestauranteRequest.getUsuario(), e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(authenticatedRestauranteToken, HttpStatus.OK);
    }

    @RequestMapping(value = GET_RESTAURANTES, method = RequestMethod.GET)
    public ResponseEntity getRestaurantes() {
        Restaurantes restaurantes;
        try {
            restaurantes = restauranteService.getAllRestaurantes(errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when finding all Restaurantes", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = GET_RESTAURANTE_BY_UUID, method = RequestMethod.GET)
    public ResponseEntity<Object> getRestauranteById(@RequestParam(value = "uuid", required = true) String uuid) {
        Restaurante restaurante;
        try {
            restaurante = restauranteService.getRestauranteByUuid(uuid, errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when finding Restaurante with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }
}
