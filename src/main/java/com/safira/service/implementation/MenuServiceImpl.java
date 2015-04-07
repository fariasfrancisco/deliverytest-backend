package com.safira.service.implementation;

import com.safira.api.CreateMenuRequest;
import com.safira.common.SafiraUtils;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Menus;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.MenuPedido;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.service.Validator;
import com.safira.service.interfaces.MenuService;
import com.safira.service.interfaces.PedidoService;
import com.safira.service.interfaces.RestauranteService;
import com.safira.service.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public Menu createMenu(CreateMenuRequest createMenuRequest) throws ValidatorException, EmptyQueryResultException {
        Validator.validateMenu(createMenuRequest);
        Restaurante restaurante = restauranteService.getRestauranteByUuid(createMenuRequest.getRestauranteUuid());
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
    public Menu getMenuByUuid(String uuid) throws EmptyQueryResultException {
        Menu menu = menuRepository.findByUuid(uuid);
        if (menu == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No menu found with uuid = " + uuid);
        return menu;
    }

    @Transactional
    public Menus getMenusByRestauranteUuid(String uuid) throws EmptyQueryResultException {
        Restaurante restaurante = restauranteService.getRestauranteByUuid(uuid);
        Menus menus = new Menus(SafiraUtils.toList(restaurante.getMenus()));
        if (menus.getMenus().isEmpty())
            throw new EmptyQueryResultException("No menus were found by the given criteria");
        return menus;
    }

    @Transactional
    public Menus getMenusByPedidoUuid(String uuid) throws EmptyQueryResultException {
        Pedido pedido = pedidoService.getPedidoByUuid(uuid);
        List<Menu> menuList = new ArrayList<>();
        for (MenuPedido menuPedido : pedido.getMenuPedidos()) menuList.add(menuPedido.getMenu());
        Menus menus = new Menus(SafiraUtils.toList(menuList));
        if (menus.getMenus().isEmpty())
            throw new EmptyQueryResultException("No menus were found by the given criteria");
        return menus;
    }
}
