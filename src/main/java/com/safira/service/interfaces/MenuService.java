package com.safira.service.interfaces;

import com.safira.api.CreateMenuRequest;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Menus;
import com.safira.domain.entities.Menu;

/**
 * Created by francisco on 24/03/15.
 */
public interface MenuService {
    public Menu createMenu(CreateMenuRequest createMenuRequest) throws ValidatorException, JPAQueryException;

    public Menu getMenuByUuid(String uuid) throws JPAQueryException;

    public Menus getMenusByRestauranteUuid(String uuid) throws JPAQueryException;

    public Menus getMenusByPedidoUuid(String uuid) throws JPAQueryException;
}
