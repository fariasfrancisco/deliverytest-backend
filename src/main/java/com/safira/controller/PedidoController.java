package com.safira.controller;

import com.safira.domain.Pedidos;
import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.Usuario;
import com.safira.domain.repositories.MenuRepository;
import com.safira.domain.repositories.PedidoRepository;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.domain.repositories.UsuarioRepository;
import com.safira.service.deserialize.PedidoDeserializer;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by francisco on 22/03/15.
 */
@RestController
public class PedidoController {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    MenuRepository menuRepository;

    final static Logger pedidoLogger = Logger.getLogger("pedidoLogger");
    final static Logger pedidoWarnLogger = Logger.getLogger("pedidoWarnLogger");
    final static Logger pedidoErrorLogger = Logger.getLogger("pedidoExceptionLogger");

    @RequestMapping(value = "/registerPedido", method = RequestMethod.POST)
    public ResponseEntity<Object> registerPedido(@RequestBody SerializedObject serializedObject) {
        Pedido pedido;
        String serializedPedido = serializedObject.getSerializedObject();
        try {
            PedidoDeserializer pedidoDeserializer =
                    new PedidoDeserializer(serializedPedido, restauranteRepository, usuarioRepository, menuRepository);
            pedido = pedidoDeserializer.getPedido();
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        try {
            pedidoRepository.save(pedido);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidoByUuid", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidoById(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedido pedido;
        try {
            pedido = pedidoRepository.findByUuid(uuid);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestaurante", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByRestaurante(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        //TODO check exceptions
        try {
            Restaurante restaurante = restauranteRepository.findByUuid(uuid);
            pedidos = new Pedidos(pedidoRepository.findByRestaurante(restaurante));
            if (pedidos.getPedidos().isEmpty()) {
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByUsuario(@RequestParam(value = "uuid", required = true) String uuid) {
        Pedidos pedidos;
        //TODO check exceptions
        try {
            Usuario usuario = usuarioRepository.findByUuid(uuid);
            pedidos = new Pedidos(pedidoRepository.findByUsuario(usuario));
            if (pedidos.getPedidos().isEmpty()) {
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPedidosByRestauranteAndUsuario", method = RequestMethod.GET)
    public ResponseEntity<Object> getPedidosByRestauranteAndUsuario(
            @RequestParam(value = "resuuid", required = true) String resuuid,
            @RequestParam(value = "usruuid", required = true) String usruuid) {
        Pedidos pedidos;
        //TODO check exceptions
        try {
            Usuario usuario = usuarioRepository.findByUuid(usruuid);
            Restaurante restaurante = restauranteRepository.findByUuid(resuuid);
            pedidos = new Pedidos(pedidoRepository.findByUsuarioAndRestaurante(usuario, restaurante));
            if (pedidos.getPedidos().isEmpty()) {
                return new ResponseEntity<>(pedidos, HttpStatus.NOT_FOUND);
            }
        } catch (HibernateException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}

