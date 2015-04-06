package com.safira.controller;

import com.safira.api.CreatePedidoRequest;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.Pedido;
import com.safira.service.interfaces.PedidoService;
import com.safira.service.log.PedidosXMLWriter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.safira.common.URLs.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    final static Logger pedidoLogger = Logger.getLogger("pedidoLogger");
    final static Logger pedidoErrorLogger = Logger.getLogger("pedidoExceptionLogger");

    @RequestMapping(value = REGISTER_PEDIDO, method = RequestMethod.POST)
    public ResponseEntity registerPedido(@RequestBody CreatePedidoRequest createPedidoRequest) {
        Pedido pedido;
        try {
            pedido = pedidoService.createPedido(createPedidoRequest);
            pedidoLogger.info("Successfully created new Pedido: \n" +
                    PedidosXMLWriter.createDocument(pedido).getDocument());
        } catch (Exception e) {
            pedidoErrorLogger.error("An exception has occured when creating a new Pedido.", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = GET_PEDIDO_BY_UUID, method = RequestMethod.GET)
    public ResponseEntity getPedidoById(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedido pedido;
        try {
            pedido = pedidoService.getPedidoByUuid(uuid);
        } catch (Exception e) {
            pedidoErrorLogger.error("An exception has occured when finding Pedido with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = GET_PEDIDOS_BY_RESTAURANTE, method = RequestMethod.GET)
    public ResponseEntity getPedidosByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        try {
            pedidos = pedidoService.getPedidosByRestauranteUuid(uuid);
        } catch (Exception e) {
            pedidoErrorLogger.error("An exception has occured when finding Pedido with Restaurante uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = GET_PEDIDOS_BY_USUARIO, method = RequestMethod.GET)
    public ResponseEntity getPedidosByUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        try {
            pedidos = pedidoService.getPedidosByUsuarioUuid(uuid);
        } catch (Exception e) {
            pedidoErrorLogger.error("An exception has occured when finding Pedido with Usuario uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = GET_PEDIDOS_BY_USUARIO_AND_BY_RESTAURANTE, method = RequestMethod.GET)
    public ResponseEntity getPedidosByRestauranteAndUsuario(
            @RequestParam(value = "resuuid", required = true) String resuuid,
            @RequestParam(value = "usruuid", required = true) String usruuid) {
        Pedidos pedidos;
        try {
            pedidos = pedidoService.getPedidosByUsuarioUuidAndByRestauranteUuid(usruuid, resuuid);
        } catch (Exception e) {
            pedidoErrorLogger.error("An exception has occured when finding Pedido with" +
                    " Restaurante uuid = " + resuuid +
                    " Usuario uuid = " + usruuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}
