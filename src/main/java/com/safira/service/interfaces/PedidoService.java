package com.safira.service.interfaces;

import com.safira.api.CreatePedidoRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.PedidoTimeoutException;
import com.safira.domain.entities.Pedido;

import java.util.List;

/**
 * Created by francisco on 24/03/15.
 */
public interface PedidoService {
    Pedido createPedido(CreatePedidoRequest createPedidoRequest, ErrorOutput errorOutput) throws InconsistencyException, PedidoTimeoutException, EmptyQueryResultException;

    Pedido getPedidoByUuid(String uuid, ErrorOutput errorOutput);

    List<Pedido> getPedidosByRestauranteUuid(String uuid, ErrorOutput errorOutput);

    List<Pedido> getPedidosByUsuarioUuid(String uuid, ErrorOutput errorOutput);

    List<Pedido> getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid, ErrorOutput errorOutput);
}
