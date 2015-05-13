package com.safira.api.controller;

import com.safira.api.requests.CreateRestauranteRequest;
import com.safira.api.requests.LoginRestauranteRequest;
import com.safira.api.responses.AuthenticatedRestauranteToken;
import com.safira.api.responses.TokenVerificationResult;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.LoginException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Restaurante;
import com.safira.service.Validator;
import com.safira.service.interfaces.RestauranteService;
import com.safira.service.log.RestauranteXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.safira.common.URLs.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ErrorOutput errors;

    final static Logger restauranteLogger = Logger.getLogger("restauranteLogger");
    final static Logger restauranteErrorLogger = Logger.getLogger("restauranteErrorLogger");

    @RequestMapping(value = REGISTER_RESTAURANTE, method = RequestMethod.POST)
    public ResponseEntity registerRestaurante(@RequestBody CreateRestauranteRequest createRestauranteRequest) {
        errors.flush();
        Restaurante restaurante;
        try {
            Validator.validateRestaurante(createRestauranteRequest, errors);
            restaurante = restauranteService.createRestaurante(createRestauranteRequest);
            restauranteLogger.info("Successfully created new Restaurante: \n" +
                    RestauranteXMLWriter.createDocument(restaurante).asXML());
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when creating a new Restaurante.", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.CREATED);
    }

    @RequestMapping(value = LOGIN_RESTAURANTE, method = RequestMethod.POST)
    public ResponseEntity loginRestaurante(@RequestBody LoginRestauranteRequest loginRestauranteRequest) {
        errors.flush();
        AuthenticatedRestauranteToken authenticatedRestauranteToken;
        try {
            Validator.validateRestauranteLogin(loginRestauranteRequest, errors);
            authenticatedRestauranteToken = restauranteService.loginRestaurante(loginRestauranteRequest, errors);
            restauranteLogger.info("Successfully logged Restaurante: " + authenticatedRestauranteToken.getRestauranteUuid() +
                    "with token: " + authenticatedRestauranteToken.getToken());
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (EmptyQueryResultException | LoginException e) {
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            restauranteErrorLogger.error("Failed attempt to log in Restaurante with usuario = "
                    + loginRestauranteRequest.getUsuario(), e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(authenticatedRestauranteToken, HttpStatus.OK);
    }

    @RequestMapping(value = VERIFY_TOKEN, method = RequestMethod.POST)
    public ResponseEntity verifyAuthenticationToken(@RequestBody AuthenticatedRestauranteToken authenticatedRestauranteToken) {
        errors.flush();
        TokenVerificationResult tokenVerificationResult;
        try {
            Validator.validateAuthenticationToken(authenticatedRestauranteToken, errors);
            tokenVerificationResult = restauranteService.verififyAuthenticationToken(authenticatedRestauranteToken, errors);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(tokenVerificationResult, HttpStatus.OK);
    }

    @RequestMapping(value = GET_RESTAURANTES + PAGINATION, method = RequestMethod.GET)
    public ResponseEntity getRestaurantes(@PathVariable(value = "pageNumber") int pagenumber) {
        errors.flush();
        List<Restaurante> restaurantes;
        try {
            Validator.validatePageNumber(pagenumber,errors);
            restaurantes = restauranteService.getAllRestaurantes(pagenumber, errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when finding all Restaurantes", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    @RequestMapping(value = GET_RESTAURANTE_BY_UUID, method = RequestMethod.GET)
    public ResponseEntity<Object> getRestauranteById(@RequestParam(value = "uuid", required = true) String uuid) {
        errors.flush();
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

    @RequestMapping(value = GET_RESTAURANTES_BY_NOMBRE + PAGINATION, method = RequestMethod.GET)
    public ResponseEntity getRestaurantes(
            @RequestParam(value = "nombre", required = true) String nombre,
            @PathVariable(value = "pageNumber") int pagenumber) {
        errors.flush();
        List<Restaurante> restaurantes;
        try {
            Validator.validatePageNumber(pagenumber,errors);
            restaurantes = restauranteService.getRestaurantesByNombre(nombre, pagenumber, errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            restauranteErrorLogger.error("An exception has occured when finding all Restaurantes", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }
}
