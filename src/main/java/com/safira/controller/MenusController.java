package com.safira.controller;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Menus;
import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.repositories.MenuRepository;
import com.safira.domain.repositories.PedidoRepository;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.service.deserialize.MenuDeserializer;
import com.safira.service.log.MenusXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller dedicated to serving json RESTful webservice for Menus
 */
@RestController
public class MenusController {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    final static Logger menuLogger = Logger.getLogger("menuLogger");
    final static Logger menuWarnLogger = Logger.getLogger("menuWarnLogger");
    final static Logger menuErrorLogger = Logger.getLogger("menuErrorLogger");

    @RequestMapping(value = "/registerMenu", method = RequestMethod.POST)
    public ResponseEntity<Object> registerMenu(@RequestBody SerializedObject serializedObject) {
        Menu menu;
        String serializedMenu = serializedObject.getSerializedObject();
        try {
            MenuDeserializer menuDeserializer = new MenuDeserializer(serializedMenu, restauranteRepository);
            menu = menuDeserializer.getMenu();
        } catch (DeserializerException | ValidatorException | JPAQueryException e) {
            menuErrorLogger.error("An error occured when deserializing recieved String", e);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        try {
            menuRepository.save(menu);
        } catch (Exception e) {
            menuErrorLogger.error("An error occured wehen registering Menu", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        menuLogger.info("Successfully registered Menu: \n" + MenusXMLWriter.createDocument(menu).asXML());
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenuByUuid", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenuById(@RequestParam(value = "uuid", required = true) String uuid) {
        Menu menu;
        try {
            menu = menuRepository.findByUuid(uuid);
        } catch (Exception e) {
            menuErrorLogger.error("An error occured when retrieving Menus with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByRestaurante", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenusByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        Menus menus;
        try {
            Restaurante restaurante = restauranteRepository.findByUuid(uuid);
            menus = new Menus(menuRepository.findByRestaurante(restaurante));
            if (menus.getMenus().isEmpty()) {
                menuWarnLogger.warn("No Menus found with restauranteUUID = " + uuid);
                return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            menuErrorLogger.error("An error occured when retrieving Menus with restauranteUUID = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @RequestMapping(value = "/getMenusByPedido", method = RequestMethod.GET)
    public ResponseEntity<Object> getMenusByPedido(@RequestParam(value = "uuid", required = true) String uuid) {
        Menus menus;
        try {
            Pedido pedido = pedidoRepository.findByUuid(uuid);
            Set<Pedido> pedidos = new HashSet<>();
            pedidos.add(pedido);
            menus = new Menus(menuRepository.findByPedidos(pedidos));
            if (menus.getMenus().isEmpty()) {
                menuWarnLogger.warn("No Menus found with pedidoUUID = " + uuid);
                return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            menuErrorLogger.error("An error occured when retrieving Menus with pedidoUUID = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}
