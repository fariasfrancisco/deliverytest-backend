package com.safira.controller;

import com.safira.api.CreatePedidoRequest;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.Pedido;
import com.safira.service.implementation.PedidoServiceImpl;
import com.safira.service.interfaces.PedidoService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class PedidoController {

    final PedidoService pedidoService = new PedidoServiceImpl();

    final static Logger pedidoLogger = Logger.getLogger("pedidoLogger");
    final static Logger pedidoWarnLogger = Logger.getLogger("pedidoWarnLogger");
    final static Logger pedidoErrorLogger = Logger.getLogger("pedidoExceptionLogger");

    @RequestMapping(value = "/registerPedido", method = RequestMethod.POST)
    public ResponseEntity<Object> registerPedido(@RequestBody CreatePedidoRequest createPedidoRequest) {
        Pedido pedido;
        try {
            pedido = pedidoService.createPedido(createPedidoRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidoByUuid", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidoById(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedido pedido;
        try {
            pedido = pedidoService.getPedidoByUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestaurante", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        try {
            pedidos = pedidoService.getPedidosByRestauranteUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        try {
            pedidos = pedidoService.getPedidosByUsuarioUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestauranteAndUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByRestauranteAndUsuario(
            @RequestParam(value = "resuuid", required = true) String resuuid,
            @RequestParam(value = "usruuid", required = true) String usruuid) {
        Pedidos pedidos;
        try {
            pedidos = pedidoService.getPedidosByUsuarioUuidAndByRestauranteUuid(usruuid, resuuid);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}

