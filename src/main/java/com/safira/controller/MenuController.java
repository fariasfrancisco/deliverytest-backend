package com.safira.controller;

import com.safira.api.CreateMenuRequest;
import com.safira.domain.Menus;
import com.safira.domain.entities.Menu;
import com.safira.service.interfaces.MenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    final static Logger menuLogger = Logger.getLogger("menuLogger");
    final static Logger menuWarnLogger = Logger.getLogger("menuWarnLogger");
    final static Logger menuErrorLogger = Logger.getLogger("menuErrorLogger");

    @RequestMapping(value = "/registerMenu", method = RequestMethod.POST)
    public ResponseEntity registerMenu(@RequestBody CreateMenuRequest createMenuRequest) {
        Menu menu;
        try {
            menu = menuService.createMenu(createMenuRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenuByUuid", method = RequestMethod.GET)
    public ResponseEntity getMenuById(@RequestParam(value = "uuid", required = true) String uuid) {
        Menu menu;
        try {
            menu = menuService.getMenuByUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByRestaurante", method = RequestMethod.GET)
    public ResponseEntity getMenusByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        Menus menus;
        try {
            menus = menuService.getMenusByRestauranteUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByPedido", method = RequestMethod.GET)
    public ResponseEntity getMenusByPedido(@RequestParam(value = "uuid", required = true) String uuid) {
        Menus menus;
        try {
            menus = menuService.getMenusByPedidoUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}
