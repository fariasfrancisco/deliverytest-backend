package com.safira.service.implementation;

import com.safira.api.CreateMenuRequest;
import com.safira.common.ErrorDescription;
import com.safira.common.ErrorOutput;
import com.safira.common.SafiraUtils;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.MenuPedido;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.service.interfaces.MenuService;
import com.safira.service.interfaces.PedidoService;
import com.safira.service.interfaces.RestauranteService;
import com.safira.service.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by francisco on 24/03/15.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    MenuRepository menuRepository;

    @Transactional
    public Menu createMenu(CreateMenuRequest createMenuRequest, ErrorOutput errors) throws EmptyQueryResultException {
        String restauranteUuid = createMenuRequest.getRestauranteUuid();
        Restaurante restaurante = restauranteService.getRestauranteByUuid(restauranteUuid, errors);
        if (errors.hasErrors()) throw new EmptyQueryResultException();
        Menu menu = new Menu.Builder()
                .withNombre(createMenuRequest.getNombre())
                .withDescripcion(createMenuRequest.getDescripcion())
                .withCosto(createMenuRequest.getCosto())
                .withRestaurante(restaurante)
                .build();
        menuRepository.save(menu);
        return menu;
    }

    @Transactional
    public Menu getMenuByUuid(String uuid, ErrorOutput errors) {
        Menu menu = menuRepository.findByUuid(uuid);
        if (menu == null) {
            errors.setMessage("Empty Query Result.");
            String field = "menuUuid";
            String message = "No menu found with uuid = " + uuid + '.';
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return menu;
    }

    @Transactional
    public List<Menu> getMenusByRestauranteUuid(String uuid, ErrorOutput errors) {
        List<Menu> menus = new ArrayList<>();
        Restaurante restaurante = restauranteService.getRestauranteByUuid(uuid, errors);
        if (errors.hasErrors()) return menus;
        menus = SafiraUtils.toList(restaurante.getMenus());
        if (menus.isEmpty()) {
            errors.setMessage("Empty Query Result.");
            String field = "restauranteUuid";
            String message = "No menus were found by the given criteria.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return menus;
    }

    @Transactional
    public List<Menu> getMenusByPedidoUuid(String uuid, ErrorOutput errors) {
        List<Menu> menus = new ArrayList<>();
        Pedido pedido = pedidoService.getPedidoByUuid(uuid, errors);
        if (errors.hasErrors()) return menus;
        List<Menu> menuList = pedido.getMenuPedidos()
                .stream()
                .map(MenuPedido::getMenu)
                .collect(Collectors.toList());
        menus = SafiraUtils.toList(menuList);
        if (menus.isEmpty()) {
            errors.setMessage("Empty Query Result.");
            String field = "pedidoUuid";
            String message = "No menus were found by the given criteria.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return menus;
    }
}
