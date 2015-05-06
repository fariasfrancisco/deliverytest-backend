package com.safira.api.controller;

import com.safira.api.requests.CreateDireccionRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Direccion;
import com.safira.service.Validator;
import com.safira.service.interfaces.DireccionService;
import com.safira.service.log.DireccionXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.safira.common.URLs.REGISTER_DIRECCION;

/**
 * Created by francisco on 06/04/15.
 */
@RestController
public class DireccionController {

    @Autowired
    DireccionService direccionService;

    private ErrorOutput errors;

    final static Logger direccionLogger = Logger.getLogger("direccionLogger");
    final static Logger direccionErrorLogger = Logger.getLogger("direccionErrorLogger");

    @RequestMapping(value = REGISTER_DIRECCION, method = RequestMethod.POST)
    public ResponseEntity addDireccion(@RequestBody CreateDireccionRequest createDireccionRequest) {
        errors = new ErrorOutput();
        Direccion direccion;
        try {
            Validator.validateDireccion(createDireccionRequest, errors);
            direccion = direccionService.createDireccion(createDireccionRequest, errors);
            direccionLogger.info("Successfully created new Direccion: " +
                    DireccionXMLWriter.createDocument(direccion).asXML());
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            direccionErrorLogger.error("An exception has occured when creating a new Direccion.", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(direccion, HttpStatus.CREATED);
    }
}
