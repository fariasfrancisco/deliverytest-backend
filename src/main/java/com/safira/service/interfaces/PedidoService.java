package com.safira.service.interfaces;

import com.safira.api.CreatePedidoRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.PedidoTimeoutException;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.Pedido;

/**
 * Created by francisco on 24/03/15.
 */
public interface PedidoService {
    public Pedido createPedido(CreatePedidoRequest createPedidoRequest, ErrorOutput errorOutput) throws InconsistencyException, PedidoTimeoutException, EmptyQueryResultException;

    public Pedido getPedidoByUuid(String uuid, ErrorOutput errorOutput);

    public Pedidos getPedidosByRestauranteUuid(String uuid, ErrorOutput errorOutput);

    public Pedidos getPedidosByUsuarioUuid(String uuid, ErrorOutput errorOutput);

    public Pedidos getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid, ErrorOutput errorOutput);
}
