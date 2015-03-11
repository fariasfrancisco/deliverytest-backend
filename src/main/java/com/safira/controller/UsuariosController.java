package com.safira.controller;

import com.safira.common.DeserializerException;
import com.safira.common.ErrorObject;
import com.safira.domain.SerializedObject;
import com.safira.entities.Usuario;
import com.safira.service.deserialize.UsuarioDeserializer;
import com.safira.service.hibernate.HibernateSessionService;
import com.safira.service.hibernate.QueryService;
import com.safira.service.log.UsuarioXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicated to serving json RESTful webservice for Usuarios
 *
 */
@RestController
public class UsuariosController {

    final static Logger usuarioLogger = Logger.getLogger("usuarioLogger");
    final static Logger usuarioWarnLogger = Logger.getLogger("usuarioWarnLogger");
    final static Logger usuarioErrorLogger = Logger.getLogger("usuarioErrorLogger");

    @RequestMapping(value = "/registerUsuario", method = RequestMethod.POST)
    public ResponseEntity<Object> registerUsuario(@RequestBody SerializedObject serializedObject) {
        Usuario usuario;
        try {
            String serializedUsuario = serializedObject.getSerializedObject();
            UsuarioDeserializer usuarioDeserializer = new UsuarioDeserializer(serializedUsuario);
            usuario = usuarioDeserializer.getUsuario();
        } catch (DeserializerException e) {
            usuarioErrorLogger.error("An error occured when deserializing recieved String", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            QueryService queryService = new QueryService();
            queryService.insertObject(usuario);
            HibernateSessionService.shutDown();
        } catch (Exception e) {
            usuarioErrorLogger.error("An error ocurred when registering Usuario", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        usuarioLogger.info("Successfully registered Usuario: \n" + UsuarioXMLWriter.createDocument(usuario));
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> loginUsuario(@RequestParam(value = "fbid", required = true) SerializedObject serializedObject) {
        String facebookId = serializedObject.getSerializedObject();
        Usuario usuario;
        try {
            QueryService queryService = new QueryService();
            usuario = queryService.getUsuario(facebookId);
            HibernateSessionService.shutDown();
        } catch (IndexOutOfBoundsException e) {
            usuarioWarnLogger.warn("Failed attempt to login with facebookId = " + facebookId);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            usuarioErrorLogger.error("An error ocurred when loging in Usuario with facebookId = " + facebookId, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        usuarioLogger.info("Successful login with id = " + usuario.getId());
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}