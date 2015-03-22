package com.safira.controller;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.LoginException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Usuario;
import com.safira.domain.repositories.UsuarioRepository;
import com.safira.service.deserialize.UsuarioDeserializer;
import com.safira.service.log.UsuarioXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicated to serving json RESTful webservice for Usuarios
 */
@RestController
public class UsuariosController {

    @Autowired
    UsuarioRepository usuarioRepository;

    final static Logger usuarioLogger = Logger.getLogger("usuarioLogger");
    final static Logger usuarioWarnLogger = Logger.getLogger("usuarioWarnLogger");
    final static Logger usuarioErrorLogger = Logger.getLogger("usuarioErrorLogger");

    @RequestMapping(value = "/registerUsuario", method = RequestMethod.POST)
    public ResponseEntity<Object> registerUsuario(@RequestBody SerializedObject serializedObject) {
        String serializedUsuario = serializedObject.getSerializedObject();
        Usuario usuario;
        try {
            UsuarioDeserializer usuarioDeserializer = new UsuarioDeserializer(serializedUsuario);
            usuario = usuarioDeserializer.getUsuario();
        } catch (DeserializerException | ValidatorException e) {
            usuarioErrorLogger.error("An error occured when deserializing recieved String", e);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            usuarioErrorLogger.error("An error ocurred when registering Usuario", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        usuarioLogger.info("Successfully registered Usuario: \n" + UsuarioXMLWriter.createDocument(usuario).asXML());
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> loginUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        Usuario usuario;
        try {
            usuario = usuarioRepository.findByUuid(uuid);
        } catch (IndexOutOfBoundsException e) {
            usuarioWarnLogger.warn("Failed attempt to login with uuid = " + uuid);
            return new ResponseEntity<>(new LoginException(e), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            usuarioErrorLogger.error("An error ocurred when loging in Usuario with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        usuarioLogger.info("Successful login with id = " + usuario.getId());
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
