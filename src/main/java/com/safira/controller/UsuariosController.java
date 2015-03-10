package com.safira.controller;

import com.safira.common.ErrorObject;
import com.safira.domain.SerializedObject;
import com.safira.entities.Usuario;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.Hibernate.QueryService;
import com.safira.service.Log.UsuarioXMLWriter;
import com.safira.service.UsuarioDeserializer;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicated to serving json RESTful webservice for Usuarios
 */
@RestController
public class UsuariosController {

    final static Logger usuarioLogger = Logger.getLogger("usuarioLogger");
    final static Logger usuarioWarnLogger = Logger.getLogger("usuarioWarnLogger");
    final static Logger usuarioErrorLogger = Logger.getLogger("usuarioErrorLogger");

    @RequestMapping(value = "/registerUsuario", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody SerializedObject serializedObject) {
        String serializedUsuario = serializedObject.getSerializedObject();
        UsuarioDeserializer usuarioDeserializer = new UsuarioDeserializer(serializedUsuario);
        Usuario usuario = usuarioDeserializer.getUsuario();
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
    public ResponseEntity<Object> login(@RequestParam(value = "fbid", required = true) SerializedObject serializedObject) {
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