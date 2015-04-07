package com.safira.controller;

import com.safira.api.CreateUsuarioRequest;
import com.safira.domain.entities.Usuario;
import com.safira.service.interfaces.UsuarioService;
import com.safira.service.log.UsuarioXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.safira.common.URLs.LOGIN_USUARIO;
import static com.safira.common.URLs.REGISTER_USUARIO;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    final static Logger usuarioLogger = Logger.getLogger("usuarioLogger");
    final static Logger usuarioErrorLogger = Logger.getLogger("usuarioErrorLogger");

    @RequestMapping(value = REGISTER_USUARIO, method = RequestMethod.POST)
    public ResponseEntity registerUsuario(@RequestBody CreateUsuarioRequest createUsuarioRequest) {
        Usuario usuario;
        try {
            usuario = usuarioService.createUsuario(createUsuarioRequest);
            usuarioLogger.info("Successfully created new Usuario: \n" +
                    UsuarioXMLWriter.createDocument(usuario).asXML());
        } catch (Exception e) {
            usuarioErrorLogger.error("An exception has occured when creating a new Usuario.", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = LOGIN_USUARIO, method = RequestMethod.GET)
    public ResponseEntity loginUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        Usuario usuario;
        try {
            usuario = usuarioService.getUsuarioByUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
