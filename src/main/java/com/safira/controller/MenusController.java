package com.safira.controller;

import com.safira.common.DeserializerException;
import com.safira.common.ErrorObject;
import com.safira.domain.Menus;
import com.safira.domain.SerializedObject;
import com.safira.entities.Menu;
import com.safira.service.deserialize.MenuDeserializer;
import com.safira.service.hibernate.HibernateSessionService;
import com.safira.service.hibernate.QueryService;
import com.safira.service.log.MenusXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
  * Controller dedicated to serving json RESTful webservice for Menus
 */
@RestController
public class MenusController {

    final static Logger menuLogger = Logger.getLogger("menuLogger");
    final static Logger menuWarnLogger = Logger.getLogger("menuWarnLogger");
    final static Logger menuErrorLogger = Logger.getLogger("menuErrorLogger");

    @RequestMapping(value = "/registerMenu", method = RequestMethod.POST)
    public ResponseEntity<Object> registerMenu(@RequestBody SerializedObject serializedObject) {
        Menu menu;
        try {
            String serializedMenu = serializedObject.getSerializedObject();
            MenuDeserializer menuDeserializer = new MenuDeserializer(serializedMenu);
            menu = menuDeserializer.getMenu();
        } catch (DeserializerException e) {
            menuErrorLogger.error("An error occured when deserializing recieved String", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            QueryService queryService = new QueryService();
            queryService.insertObject(menu);
        } catch (Exception e) {
            menuErrorLogger.error("An error occured wehen registering Menu", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        menuLogger.info("Successfully registered Menu: \n" + MenusXMLWriter.createDocument(menu).asXML());
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenuById", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenuById(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
        int menuid;
        try {
            menuid = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            menuid = 0;
        }
        Menu menu;
        try {
            QueryService queryService = new QueryService();
            menu = queryService.getMenuById(menuid);
        } catch (Exception e) {
            menuErrorLogger.error("An error occured when retrieving Menus with id = " + id, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByRestaurante", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenusByRestaurante(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
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
            if (menus.getMenus().isEmpty()) {
                menuWarnLogger.warn("No Menus found with restauranteId = " + id);
                return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            menuErrorLogger.error("An error occured when retrieving Menus with restauranteId = " + id, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByPedido", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenusByPedido(@RequestParam(value = "id", required = true, defaultValue = "0") String id) {
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
            if (menus.getMenus().isEmpty()) {
                menuWarnLogger.warn("No Menus found with pedidoId = " + id);
                return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            menuErrorLogger.error("An error occured when retrieving Menus with pedidoId = " + id, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}