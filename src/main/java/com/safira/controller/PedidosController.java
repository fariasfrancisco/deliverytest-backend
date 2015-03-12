package com.safira.controller;

import com.safira.common.DeserializerException;
import com.safira.common.ErrorObject;
import com.safira.domain.Pedidos;
import com.safira.domain.SerializedObject;
import com.safira.entities.Pedido;
import com.safira.service.deserialize.PedidoDeserializer;
import com.safira.service.hibernate.HibernateSessionService;
import com.safira.service.hibernate.QueryService;
import com.safira.service.log.PedidosXMLWriter;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by francisco on 01/03/15.
 */
@RestController
public class PedidosController {

    final static Logger pedidoLogger = Logger.getLogger("pedidoLogger");
    final static Logger pedidoWarnLogger = Logger.getLogger("pedidoWarnLogger");
    final static Logger pedidoErrorLogger = Logger.getLogger("pedidoExceptionLogger");

    @RequestMapping(value = "/registerPedido", method = RequestMethod.POST)
    public ResponseEntity<Object> registerPedido(@RequestBody SerializedObject serializedObject) {
        Pedido pedido;
        try {
            String serializedPedido = serializedObject.getSerializedObject();
            PedidoDeserializer pedidoDeserializer = new PedidoDeserializer(serializedPedido);
            pedido = pedidoDeserializer.getPedido();
        } catch (DeserializerException e) {
            pedidoErrorLogger.error("An error occured when deserializing recieved String", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            QueryService queryService = new QueryService();
            queryService.insertObject(pedido);
        } catch (Exception e) {
            pedidoErrorLogger.error("An error occured when registering a Pedido", e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        pedidoLogger.info("Successfully registered Pedido: \n" + PedidosXMLWriter.createDocument(pedido).asXML());
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidoById", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidoById(@RequestParam(value = "id", required = true) String id) {
        Pedido pedido;
        try {
            QueryService queryService = new QueryService();
            pedido = queryService.getPedidoById(Integer.valueOf(id));
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with id = " + id, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            pedidoWarnLogger.warn("No Pedido was found with id = " + id);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestauranteId", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByRestauranteId(@RequestParam(value = "id", required = true) String restauranteId) {
        Pedidos pedidos;
        try {
            QueryService queryService = new QueryService();
            pedidos = new Pedidos(queryService.getPedidosByRestauranteId(Integer.valueOf(restauranteId)));
            if (pedidos.getPedidos().isEmpty()) {
                pedidoWarnLogger.warn("No Pedido was found with restauranteId = " + restauranteId);
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with restauranteId = " + restauranteId, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByUsuarioId", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByUsuarioId(@RequestParam(value = "id", required = true) String usuarioId) {
        Pedidos pedidos;
        try {
            QueryService queryService = new QueryService();
            pedidos = new Pedidos(queryService.getPedidosByUsuarioId(Integer.valueOf(usuarioId)));
            if (pedidos.getPedidos().isEmpty()) {
                pedidoWarnLogger.warn("No Pedido was found with usuarioId = " + usuarioId);
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with" +
                    " usuarioId = " + usuarioId, e);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestauranteIdAndUsuarioId", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByRestauranteIdAndUsuarioId(
            @RequestParam(value = "resid", required = true) String restauranteId,
            @RequestParam(value = "usrid", required = true) String usuarioId) {
        Pedidos pedidos;
        try {
            QueryService queryService = new QueryService();
            pedidos = new Pedidos(queryService.
                    getPedidosByRestauranteIdAndUsuarioId(Integer.valueOf(restauranteId), Integer.valueOf(usuarioId)));
            if (pedidos.getPedidos().isEmpty()) {
                pedidoWarnLogger.warn("No Pedido was found with usuarioId = " + usuarioId +
                        " and  restauranteId = " + restauranteId);
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with" +
                    " usuarioId = " + usuarioId + " and  restauranteId = " + restauranteId);
            return new ResponseEntity<>(new ErrorObject(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            HibernateSessionService.shutDown();
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}