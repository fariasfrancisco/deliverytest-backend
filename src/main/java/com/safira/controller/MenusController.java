package com.safira.controller;

import com.safira.common.ErrorObject;
import com.safira.domain.Menus;
import com.safira.entities.Menu;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.Hibernate.QueryService;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * Controller dedicated to serving json RESTful webservice for Menus
 */
public class MenusController {

    @RequestMapping(value = "/getMenusByRestaurante", method = RequestMethod.GET)
    public ResponseEntity<Object> menusByRestaurante(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        Menus menus;
        try {
            QueryService queryService = new QueryService();
            menus = new Menus(queryService.getMenusByRestauranteId(restauranteId));
            HibernateSessionService.shutDown();
            if (menus.getMenus().isEmpty()) {
                //TODO log NOTFOUND
                return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //TODO log stacktrace
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByPedido", method = RequestMethod.GET)
    public ResponseEntity<Object> menusByPedido(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int pedidoId;
        try {
            pedidoId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            pedidoId = 0;
        }
        Menus menus;
        try {
            QueryService queryService = new QueryService();
            menus = new Menus(queryService.getMenusByPedidoId(pedidoId));
            HibernateSessionService.shutDown();
            if (menus.getMenus().isEmpty()) {
                //TODO log NOTFOUND
                return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //TODO log stacktrace
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }


    @RequestMapping(value = "/insertMenu", method = RequestMethod.POST)
    public ResponseEntity<Object> menu(@RequestBody Menu menu) {
        try {
            QueryService queryService = new QueryService();
            menu = new Menu(menu);
            queryService.insertObject(menu);
            HibernateSessionService.shutDown();
        } catch (Exception e) {
            //TODO Log Menu's failed insert & stacktrace
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }//TODO Log Menu's successful insert
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
}