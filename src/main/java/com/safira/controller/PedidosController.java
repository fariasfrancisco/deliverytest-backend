package com.safira.controller;

import com.safira.common.exceptions.DeserializerException;
import com.safira.domain.Pedidos;
import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.Usuario;
import com.safira.domain.repositories.PedidoRepository;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.domain.repositories.UsuarioRepository;
import com.safira.service.deserialize.PedidoDeserializer;
import com.safira.service.log.PedidosXMLWriter;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by francisco on 01/03/15.
 */
@RestController
public class PedidosController {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

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
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        try {
            pedidoRepository.save(pedido);
        } catch (Exception e) {
            pedidoErrorLogger.error("An error occured when registering a Pedido", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        pedidoLogger.info("Successfully registered Pedido: \n" + PedidosXMLWriter.createDocument(pedido).asXML());
        return new ResponseEntity<>(pedido, HttpStatus.OK);

    }

    @RequestMapping(value = "/getPedidoByUuid", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidoById(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedido pedido;
        try {
            pedido = pedidoRepository.findByUuid(uuid);
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with uuid = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            pedidoWarnLogger.warn("No Pedido was found with uuid = " + uuid);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestaurante", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        try {
            Restaurante restaurante = restauranteRepository.findByUuid(uuid);
            pedidos = new Pedidos(pedidoRepository.findByRestaurante(restaurante));
            if (pedidos.getPedidos().isEmpty()) {
                pedidoWarnLogger.warn("No Pedido was found with restauranteUUID = " + uuid);
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with restauranteUUID = " + uuid, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        try {
            Usuario usuario = usuarioRepository.findByUuid(uuid);
            pedidos = new Pedidos(pedidoRepository.findByUsuario(usuario));
            if (pedidos.getPedidos().isEmpty()) {
                pedidoWarnLogger.warn("No Pedido was found with usuarioUUID = " + uuid);
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with usuarioUUID = " + uuid, e);
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
            Usuario usuario = usuarioRepository.findByUuid(usruuid);
            Restaurante restaurante = restauranteRepository.findByUuid(resuuid);
            pedidos = new Pedidos(pedidoRepository.findByUsuarioAndRestaurante(usuario, restaurante));
            if (pedidos.getPedidos().isEmpty()) {
                pedidoWarnLogger.warn("No Pedido was found with usuarioUUID = " + usruuid +
                        " and  restauranteUUID = " + resuuid);
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            pedidoErrorLogger.error("An error occured when retrieving Pedido with" +
                    " usuarioUUID = " + usruuid + " and  restauranteUUID = " + resuuid);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}
