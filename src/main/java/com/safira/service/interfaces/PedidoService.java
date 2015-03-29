package com.safira.service.interfaces;

import com.safira.api.CreatePedidoRequest;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.Pedido;

/**
 * Created by francisco on 24/03/15.
 */
public interface PedidoService {
    public Pedido createPedido(CreatePedidoRequest createPedidoRequest) throws ValidatorException, EmptyQueryResultException, InconsistencyException;

    public Pedido getPedidoByUuid(String uuid) throws EmptyQueryResultException;

    public Pedidos getPedidosByRestauranteUuid(String uuid) throws EmptyQueryResultException;

    public Pedidos getPedidosByUsuarioUuid(String uuid) throws EmptyQueryResultException;

    public Pedidos getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid) throws EmptyQueryResultException;
}
