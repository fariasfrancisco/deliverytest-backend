package com.safira.controller;

import com.safira.common.ErrorObject;
import com.safira.domain.Pedidos;
import com.safira.entities.Pedido;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.Hibernate.QueryService;
import com.safira.service.Log.PedidosXMLWriter;
import com.safira.service.PedidoDeserializer;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
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

    final static Logger pedidoLogger = Logger.getLogger("pedidoLogger");
    final static Logger pedidoWarnLogger = Logger.getLogger("pedidoWarnLogger");
    final static Logger pedidoErrorLogger = Logger.getLogger("pedidoExceptionLogger");

    @RequestMapping(value = "/registerPedido", method = RequestMethod.POST)
    public ResponseEntity<Pedido> register(@RequestBody String serializedPedido) {
        PedidoDeserializer pedidoDeserializer = new PedidoDeserializer(serializedPedido);
        Pedido pedido = null;
        try {
            pedido = pedidoDeserializer.getPedido();
            QueryService queryService = new QueryService();
            queryService.insertObject(pedido);
            HibernateSessionService.shutDown();
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when registering a Pedido!", e);
            new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        pedidoLogger.info("Successfully registered Pedido: \n"
                + PedidosXMLWriter.createDocument(pedido));
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidoById", method = RequestMethod.GET)
    public ResponseEntity<Pedido> get(@RequestParam(value = "id", required = true) String id) {
        Pedido pedido = null;
        try {
            QueryService queryService = new QueryService();
            pedido = queryService.getPedidoById(Integer.valueOf(id));
            HibernateSessionService.shutDown();
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with id = " + id + "!", e);
            new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            pedidoWarnLogger.warn("No Pedido was found with id = " + id + ".");
            new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
                pedidoWarnLogger.warn("No Pedido was found with restauranteId = " + restauranteId + ".");
                new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with " +
                    "restauranteId = " + restauranteId + "!", e);
            new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
                pedidoWarnLogger.warn("No Pedido was found with usuarioId = " + usuarioId + ".");
                new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with" +
                    " usuarioId = " + usuarioId + "!", e);
            new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestauranteIdAndUsuarioId", method = RequestMethod.GET)
    public ResponseEntity<Pedidos> getByRestauranteAndUsuario(
            @RequestParam(value = "resid", required = true) String restauranteId,
            @RequestParam(value = "usrid", required = true) String usuarioId) {
        Pedidos pedidos = null;
        try {
            QueryService queryService = new QueryService();
            pedidos = new Pedidos(queryService.
                    getPedidosByRestauranteIdAndUsuarioId(Integer.valueOf(restauranteId), Integer.valueOf(usuarioId)));
            HibernateSessionService.shutDown();
            if (pedidos.getPedidos().isEmpty()) {
                pedidoWarnLogger.warn("No Pedido was found with usuarioId = " + usuarioId +
                        " and  restauranteId = " + restauranteId + ".");
                new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with" +
                    " usuarioId = " + usuarioId + " and  restauranteId = " + restauranteId + ".");
            new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}