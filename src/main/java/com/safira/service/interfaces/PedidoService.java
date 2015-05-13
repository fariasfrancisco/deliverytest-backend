package com.safira.service.interfaces;

import com.safira.api.requests.CreatePedidoRequest;
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

    List<Pedido> getPedidosByRestauranteUuid(String uuid, int pageNumber, ErrorOutput errorOutput);

    List<Pedido> getPedidosByUsuarioUuid(String uuid, int pageNumber, ErrorOutput errorOutput);

    List<Pedido> getPedidosByUsuarioUuidAndByRestauranteUuid(String usuarioUuid, String restauranteUuid, int pageNumber, ErrorOutput errorOutput);
}
