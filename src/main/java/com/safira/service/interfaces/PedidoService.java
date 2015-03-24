package com.safira.service.interfaces;

import com.safira.api.CreateFavoritoRequest;
import com.safira.api.CreatePedidoRequest;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.Favoritos;
import com.safira.domain.entities.Pedido;

/**
 * Created by francisco on 24/03/15.
 */
public interface PedidoService {
    public Pedido createPedido(CreatePedidoRequest createPedidoRequest) throws ValidatorException, JPAQueryException, InconsistencyException;

    public Favoritos addPedidoToFavoritos(CreateFavoritoRequest createFavoritoRequest);

    public Pedido getPedidoByUuid(String uuid) throws JPAQueryException;

    public Pedidos getPedidosByRestauranteUuid(String uuid) throws JPAQueryException;

    public Pedidos getPedidosByUsuarioUuid(String uuid) throws JPAQueryException;

    public Pedidos getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid) throws JPAQueryException;
}
