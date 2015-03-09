package com.safira.controller;

import com.safira.entities.Usuario;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.Hibernate.QueryService;
import com.safira.service.UsuarioDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicated to serving json RESTful webservice for Usuarios
 */
@RestController
public class UsuariosController {
    @RequestMapping(value = "/loginUsuario", method = RequestMethod.GET)
    public ResponseEntity<Usuario> login(@RequestParam(value = "fbid", required = true) String facebookId) {
        Usuario usuario = null;
        try {
            QueryService queryService = new QueryService();
            usuario = queryService.getUsuario(facebookId);
            HibernateSessionService.shutDown();
        } catch (Exception e) {
            //TODO log Usuario's failed login & stacktrace
            return new ResponseEntity<>(usuario, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO log Usuario's login
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/registerUsuario", method = RequestMethod.POST)
    public ResponseEntity<Usuario> register(@RequestBody String serializedUsuario) {
        UsuarioDeserializer usuarioDeserializer = new UsuarioDeserializer(serializedUsuario);
        Usuario usuario = null;
        try {
            usuario = usuarioDeserializer.getUsuario();
            QueryService queryService = new QueryService();
            queryService.insertObject(usuario);
        } catch (Exception e) {
            //TODO log Usuario's failed login & stacktrace
            return new ResponseEntity<>(usuario, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO log Usuarios's registration
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
