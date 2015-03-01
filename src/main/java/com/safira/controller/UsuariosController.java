package com.safira.controller;

import com.safira.domain.Usuarios;
import com.safira.entities.Usuario;
import com.safira.service.HibernateSessionService;
import com.safira.service.QueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller dedicated to serving json RESTful webservice for Usuarios
 */
@RestController
public class UsuariosController {
    @RequestMapping(value = "/getUsuarios", method = RequestMethod.GET)
    public Usuarios usuarios() {
        QueryService queryService = new QueryService();
        Usuarios usuarios = new Usuarios(queryService.GetUsuarios());
        HibernateSessionService.shutDown();
        return usuarios;
    }

    @RequestMapping(value = "/getUsuarioByFacebookId", method = RequestMethod.GET)
    public Usuario usuarioByFacebookId(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        QueryService queryService = new QueryService();
        Usuarios usuarios = new Usuarios(queryService.GetUsuario(id));
        Usuario usuario = (usuarios.get(0));
        HibernateSessionService.shutDown();
        return usuario;
    }

    @RequestMapping(value = "/getUsuarioById", method = RequestMethod.GET)
    public Usuario usuarioById(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int usuarioId;
        try {
            usuarioId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            usuarioId = 0;
        }
        QueryService queryService = new QueryService();
        Usuarios usuarios = new Usuarios(queryService.GetUsuario(usuarioId));
        Usuario usuario = (usuarios.get(0));
        HibernateSessionService.shutDown();
        return usuario;
    }
}
