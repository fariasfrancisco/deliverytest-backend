package com.safira.api.controller;

import com.safira.api.requests.CreateUsuarioRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Usuario;
import com.safira.service.Validator;
import com.safira.service.interfaces.UsuarioService;
import com.safira.service.log.UsuarioXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.safira.common.URLs.GET_USUARIO;
import static com.safira.common.URLs.REGISTER_USUARIO;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ErrorOutput errors;

    final static Logger usuarioLogger = Logger.getLogger("usuarioLogger");
    final static Logger usuarioErrorLogger = Logger.getLogger("usuarioErrorLogger");

    @RequestMapping(value = REGISTER_USUARIO, method = RequestMethod.POST)
    public ResponseEntity registerUsuario(@RequestBody CreateUsuarioRequest createUsuarioRequest) {
        errors.flush();
        Usuario usuario;
        try {
            Validator.validateUsuario(createUsuarioRequest, errors);
            usuario = usuarioService.createUsuario(createUsuarioRequest);
            usuarioLogger.info("Successfully created new Usuario: \n"
                    + UsuarioXMLWriter.createDocument(usuario).asXML());
        } catch (ValidatorException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            usuarioErrorLogger.error("An exception has occured when creating a new Usuario.", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @RequestMapping(value = GET_USUARIO, method = RequestMethod.GET)
    public ResponseEntity getUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        errors.flush();
        Usuario usuario;
        try {
            usuario = usuarioService.getUsuarioByUuid(uuid, errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
