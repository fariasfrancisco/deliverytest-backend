package com.safira.service.interfaces;

import com.safira.api.CreateMenuRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Menus;
import com.safira.domain.entities.Menu;

/**
 * Created by francisco on 24/03/15.
 */
public interface MenuService {
    Menu createMenu(CreateMenuRequest createMenuRequest, ErrorOutput errorOutput) throws EmptyQueryResultException;

    Menu getMenuByUuid(String uuid, ErrorOutput errorOutput);

    Menus getMenusByRestauranteUuid(String uuid, ErrorOutput errorOutput);

    Menus getMenusByPedidoUuid(String uuid, ErrorOutput errorOutput);
}
