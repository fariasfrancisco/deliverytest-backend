package com.safira.service.interfaces;

import com.safira.api.requests.CreateMenuRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.domain.entities.Menu;

import java.util.List;

/**
 * Created by francisco on 24/03/15.
 */
public interface MenuService {
    Menu createMenu(CreateMenuRequest createMenuRequest, ErrorOutput errorOutput) throws EmptyQueryResultException;

    Menu getMenuByUuid(String uuid, ErrorOutput errorOutput);

    List<Menu> getMenusByRestauranteUuid(String uuid, ErrorOutput errorOutput);

    List<Menu> getMenusByPedidoUuid(String uuid, ErrorOutput errorOutput);
}
