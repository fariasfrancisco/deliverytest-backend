package com.safira.controller;

import com.safira.domain.Pedidos;
import com.safira.entities.Pedido;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.PedidoDeserializer;
import com.safira.service.Hibernate.QueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by francisco on 01/03/15.
 */
public class PedidosController {

    @RequestMapping(value = "/registerPedido", method = RequestMethod.POST)
    public ResponseEntity<Pedido> register(@RequestBody String serializedPedido) {
        PedidoDeserializer pedidoDeserializer = new PedidoDeserializer(serializedPedido);
        Pedido pedido = null;
        try {
            pedido = pedidoDeserializer.getPedido();
            QueryService queryService = new QueryService();
            queryService.insertObject(pedido);
            HibernateSessionService.shutDown();
        } catch (Exception e) {
            //TODO log Pedido's failed registration & stacktrace
            new ResponseEntity<>(pedido, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO log Pedido's successful registration
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestauranteId", method = RequestMethod.GET)
    public ResponseEntity<Pedidos> getByRestaurante(@RequestParam(value = "id", required = true) String restauranteId) {
        Pedidos pedidos = null;
        try {
            QueryService queryService = new QueryService();
            pedidos = new Pedidos(queryService.getPedidosByRestauranteId(Integer.valueOf(restauranteId)));
            HibernateSessionService.shutDown();
            if (pedidos.getPedidos().isEmpty()) {
                new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //TODO log stacktrace
            new ResponseEntity<>(pedidos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByUsuarioId", method = RequestMethod.GET)
    public ResponseEntity<Pedidos> getByUsuario(@RequestParam(value = "id", required = true) String usuarioId) {
        Pedidos pedidos = null;
        try {
            QueryService queryService = new QueryService();
            pedidos = new Pedidos(queryService.getPedidosByUsuarioId(Integer.valueOf(usuarioId)));
            HibernateSessionService.shutDown();
            if (pedidos.getPedidos().isEmpty()) {
                new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //TODO log stacktrace
            new ResponseEntity<>(pedidos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}
