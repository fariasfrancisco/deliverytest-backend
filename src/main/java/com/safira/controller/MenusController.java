package com.safira.controller;

import com.safira.common.exceptions.DeserializerException;
import com.safira.domain.Menus;
import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Menu;
import com.safira.service.deserialize.MenuDeserializer;
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
        QueryService queryService = QueryService.getQueryService();
        try {
            Menu menu;
            try {
                String serializedMenu = serializedObject.getSerializedObject();
                MenuDeserializer menuDeserializer = new MenuDeserializer(serializedMenu, queryService);
                menu = menuDeserializer.getMenu();
            } catch (DeserializerException e) {
                menuErrorLogger.error("An error occured when deserializing recieved String", e);
                return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
            }
            try {
                queryService.insertObject(menu);
            } catch (Exception e) {
                menuErrorLogger.error("An error occured wehen registering Menu", e);
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            menuLogger.info("Successfully registered Menu: \n" + MenusXMLWriter.createDocument(menu).asXML());
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } finally {
            queryService.closeSession();
        }
    }

    @RequestMapping(value = "/getMenuByUuid", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenuById(@RequestParam(value = "uuid", required = true) String uuid) {
        QueryService queryService = QueryService.getQueryService();
        try {
            Menu menu;
            try {
                menu = queryService.getMenuByUuid(uuid);
            } catch (Exception e) {
                menuErrorLogger.error("An error occured when retrieving Menus with uuid = " + uuid, e);
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } finally {
            queryService.closeSession();
        }
    }

    @RequestMapping(value = "/getMenusByRestaurante", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenusByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        QueryService queryService = QueryService.getQueryService();
        try {
            Menus menus;
            try {
                menus = new Menus(queryService.getMenusByRestauranteUuid(uuid));
                if (menus.getMenus().isEmpty()) {
                    menuWarnLogger.warn("No Menus found with restauranteUUID = " + uuid);
                    return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                menuErrorLogger.error("An error occured when retrieving Menus with restauranteUUID = " + uuid, e);
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } finally {
            queryService.closeSession();
        }
    }

    @RequestMapping(value = "/getMenusByPedido", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenusByPedido(@RequestParam(value = "uuid", required = true) String uuid) {
        QueryService queryService = QueryService.getQueryService();
        try {
            Menus menus;
            try {

                menus = new Menus(queryService.getMenusByPedidoUuid(uuid));
                if (menus.getMenus().isEmpty()) {
                    menuWarnLogger.warn("No Menus found with pedidoUUID = " + uuid);
                    return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                menuErrorLogger.error("An error occured when retrieving Menus with pedidoUUID = " + uuid, e);
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } finally {
            queryService.closeSession();
        }
    }
}
