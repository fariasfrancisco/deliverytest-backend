package com.safira.controller;

import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Usuario;
import com.safira.domain.repositories.UsuarioRepository;
import com.safira.service.deserialize.UsuarioDeserializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class UsuarioController {

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
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> loginUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        Usuario usuario;
        try {
            usuario = usuarioRepository.findByUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
