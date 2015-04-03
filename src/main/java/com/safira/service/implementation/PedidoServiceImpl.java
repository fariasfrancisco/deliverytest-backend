package com.safira.service.implementation;

import com.safira.api.CreatePedidoRequest;
import com.safira.common.SafiraUtils;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.*;
import com.safira.service.Validator;
import com.safira.service.interfaces.PedidoService;
import com.safira.service.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by francisco on 24/03/15.
 */
@Service("pedidoService")
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    DireccionRepository direccionRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MenuPedidoRepository menuPedidoRepository;

    @Transactional
    public Pedido createPedido(CreatePedidoRequest createPedidoRequest) throws ValidatorException, EmptyQueryResultException, InconsistencyException {
        Validator.validatePedido(createPedidoRequest);
        Direccion direccion = direccionRepository.findByUuid(createPedidoRequest.getDireccionUuid());
        if (direccion == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No direccion found with uuid = " + createPedidoRequest.getDireccionUuid());
        Restaurante restaurante = restauranteRepository.findByUuid(createPedidoRequest.getRestauranteUuid());
        if (restaurante == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No restaurante found with uuid = " + createPedidoRequest.getRestauranteUuid());
        Usuario usuario = usuarioRepository.findByUuid(createPedidoRequest.getUsuarioUuid());
        if (usuario == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No usuario found with uuid = " + createPedidoRequest.getUsuarioUuid());
        Pedido pedido = new Pedido.Builder()
                .withDireccion(direccion)
                .withTelefono(createPedidoRequest.getTelefono())
                .withFecha(createPedidoRequest.getFecha())
                .withRestaurante(restaurante)
                .withUsuario(usuario)
                .build();
        Menu menu;
        MenuPedido menuPedido;
        int length = createPedidoRequest.getMenuUuids().length;
        for (int index = 0; index < length; index++) {
            String menuUuid = createPedidoRequest.getMenuUuids()[index];
            BigDecimal cantidad = createPedidoRequest.getCantidades()[index];
            menu = menuRepository.findByUuid(menuUuid);
            if (menu == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                    "No menu found with uuid = " + menuUuid);
            if (!restaurante.getIdentifier().equals(menu.getRestaurante().getIdentifier())) {
                throw new InconsistencyException("Deserialization Failed. " +
                        "menu.restaurante uuid recieved was " + menuUuid +
                        ". Expected " + createPedidoRequest.getRestauranteUuid());
            }
            menuPedido = new MenuPedido();
            menuPedido.setPedido(pedido);
            menuPedido.setMenu(menu);
            menuPedido.setCantidad(cantidad);
            menuPedidoRepository.save(menuPedido);
        }
        pedidoRepository.save(pedido);
        return pedido;
    }

    @Override
    public Pedido getPedidoByUuid(String uuid) throws EmptyQueryResultException {
        Pedido pedido = pedidoRepository.findByUuid(uuid);
        if (pedido == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No pedido found with uuid = " + uuid);
        return pedido;
    }

    @Override
    public Pedidos getPedidosByRestauranteUuid(String uuid) throws EmptyQueryResultException {
        Restaurante restaurante = restauranteRepository.findByUuid(uuid);
        if (restaurante == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No restaurante found with uuid = " + uuid);
        Pedidos pedidos = new Pedidos(SafiraUtils.toList(restaurante.getPedidos()));
        if (pedidos.getPedidos().isEmpty())
            throw new EmptyQueryResultException("No pedidos were found by the given criteria");
        return pedidos;
    }

    @Override
    public Pedidos getPedidosByUsuarioUuid(String uuid) throws EmptyQueryResultException {
        Usuario usuario = usuarioRepository.findByUuid(uuid);
        if (usuario == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No usuario found with uuid = " + uuid);
        Pedidos pedidos = new Pedidos(SafiraUtils.toList(usuario.getPedidos()));
        if (pedidos.getPedidos().isEmpty())
            throw new EmptyQueryResultException("No pedidos were found by the given criteria");
        return pedidos;
    }

    @Override
    public Pedidos getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid)
            throws EmptyQueryResultException {
        Usuario usuario = usuarioRepository.findByUuid(resuuid);
        if (usuario == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No usuario found with uuid = " + resuuid);
        Restaurante restaurante = restauranteRepository.findByUuid(resuuid);
        if (restaurante == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No restaurante found with uuid = " + resuuid);
        List<Pedido> pedidosByRestaurante = SafiraUtils.toList(restaurante.getPedidos());
        List<Pedido> pedidosByUsuario = SafiraUtils.toList(usuario.getPedidos());
        List<Pedido> intersection = SafiraUtils.intersection(pedidosByRestaurante, pedidosByUsuario);
        Pedidos pedidos = new Pedidos(intersection);
        if (pedidos.getPedidos().isEmpty())
            throw new EmptyQueryResultException("No pedidos were found by the given criteria");
        return pedidos;
    }
}
