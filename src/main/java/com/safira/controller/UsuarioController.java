package com.safira.controller;

import com.safira.api.CreateUsuarioRequest;
import com.safira.domain.entities.Usuario;
import com.safira.service.interfaces.UsuarioService;
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
    UsuarioService usuarioService;

    final static Logger usuarioLogger = Logger.getLogger("usuarioLogger");
    final static Logger usuarioWarnLogger = Logger.getLogger("usuarioWarnLogger");
    final static Logger usuarioErrorLogger = Logger.getLogger("usuarioErrorLogger");

    @RequestMapping(value = "/registerUsuario", method = RequestMethod.POST)
    public ResponseEntity registerUsuario(@RequestBody CreateUsuarioRequest createUsuarioRequest) {
        Usuario usuario;
        try {
            usuario = usuarioService.createUsuario(createUsuarioRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginUsuario", method = RequestMethod.GET)
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
