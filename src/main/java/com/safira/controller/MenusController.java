package com.safira.controller;

import com.safira.domain.Menus;
import com.safira.entities.Menu;
import com.safira.service.HibernateSessionService;
import com.safira.service.QueryService;
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
    public ResponseEntity<Menus> menusByRestaurante(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int restauranteId;
        try {
            restauranteId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            restauranteId = 0;
        }
        Menus menus = null;
        try {
            QueryService queryService = new QueryService();
            menus = new Menus(queryService.GetMenuByRestauranteId(restauranteId));
            HibernateSessionService.shutDown();
        } catch (HibernateException e) {

        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByPedido", method = RequestMethod.GET)
    public ResponseEntity<Menus> menusByPedido(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int pedidoId;
        try {
            pedidoId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            pedidoId = 0;
        }
        QueryService queryService = new QueryService();
        Menus menus = new Menus(queryService.GetMenusForPedido(pedidoId));
        HibernateSessionService.shutDown();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }


    @RequestMapping(value = "/insertMenu", method = RequestMethod.POST)
    public HttpStatus menu(@RequestBody Menu menu) {
        try {
            QueryService queryService = new QueryService();
            queryService = new QueryService();
            queryService.InsertObject(new Menu(menu));
            HibernateSessionService.shutDown();
        } catch (Exception e) {
            //TODO Log error
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.OK;
    }


}
