package com.safira.api.controller;

import com.safira.api.requests.CreateMenuRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Menu;
import com.safira.service.Validator;
import com.safira.service.interfaces.MenuService;
import com.safira.service.log.MenusXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.safira.common.URLs.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private ErrorOutput errors;

    final static Logger menuLogger = Logger.getLogger("menuLogger");
    final static Logger menuErrorLogger = Logger.getLogger("menuErrorLogger");

    @RequestMapping(value = REGISTER_MENU, method = RequestMethod.POST)
    public ResponseEntity registerMenu(@RequestBody CreateMenuRequest createMenuRequest) {
        errors.flush();
        Menu menu;
        try {
            Validator.validateMenu(createMenuRequest, errors);
            menu = menuService.createMenu(createMenuRequest, errors);
            menuLogger.info("Successfully created new Menu: \n" +
                    MenusXMLWriter.createDocument(menu).asXML());
        } catch (ValidatorException | EmptyQueryResultException e) {
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            menuErrorLogger.error("An exception has occured when creating a new Menu.", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }

    @RequestMapping(value = GET_MENU_BY_UUID, method = RequestMethod.GET)
    public ResponseEntity getMenuById(@RequestParam(value = "uuid", required = true) String uuid) {
        errors.flush();
        Menu menu;
        try {
            menu = menuService.getMenuByUuid(uuid, errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            menuErrorLogger.error("An exception has occured when finding Menu with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = GET_MENUS_BY_RESTAURANTE, method = RequestMethod.GET)
    public ResponseEntity getMenusByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        errors.flush();
        List<Menu> menus;
        try {
            menus = menuService.getMenusByRestauranteUuid(uuid,0, errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            menuErrorLogger.error("An exception has occured when finding Menus with Restaurante uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @RequestMapping(value = GET_MENUS_BY_PEDIDO, method = RequestMethod.GET)
    public ResponseEntity getMenusByPedido(@RequestParam(value = "uuid", required = true) String uuid) {
        errors.flush();
        List<Menu> menus;
        try {
            menus = menuService.getMenusByPedidoUuid(uuid, 0, errors);
            if (errors.hasErrors()) return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            menuErrorLogger.error("An exception has occured when finding Menus with Pedidos uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}
