package com.safira.service.implementation;

import com.safira.api.CreatePedidoRequest;
import com.safira.common.SafiraUtils;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.PedidoTimeoutException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.*;
import com.safira.service.Validator;
import com.safira.service.interfaces.*;
import com.safira.service.repositories.MenuPedidoRepository;
import com.safira.service.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by francisco on 24/03/15.
 */
@Service("pedidoService")
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    MenuService menuService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DireccionService direccionService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MenuPedidoRepository menuPedidoRepository;

    @Transactional
    public Pedido createPedido(CreatePedidoRequest createPedidoRequest) throws ValidatorException, EmptyQueryResultException, InconsistencyException, PedidoTimeoutException {
        Validator.validatePedido(createPedidoRequest);
        Direccion direccion = direccionService.getDireccionByUuid(createPedidoRequest.getDireccionUuid());
        Usuario usuario = usuarioService.getUsuarioByUuid(createPedidoRequest.getUsuarioUuid());
        Restaurante restaurante = restauranteService.getRestauranteByUuid(createPedidoRequest.getRestauranteUuid());
        if (!direccion.getUsuario().equals(usuario))
            throw new InconsistencyException("Deserialization Failed. " +
                    "The Direccion recieved does not belong to the recieved Usuario");
        if (createPedidoRequest.getFecha().isBefore(LocalDateTime.now().minusMinutes(2)))
            throw new PedidoTimeoutException();
        Pedido pedido = new Pedido.Builder()
                .withDireccion(direccion)
                .withTelefono(createPedidoRequest.getTelefono())
                .withFecha(createPedidoRequest.getFecha())
                .withRestaurante(restaurante)
                .withUsuario(usuario)
                .build();
        pedidoRepository.save(pedido);
        Menu menu;
        MenuPedido menuPedido;
        int length = createPedidoRequest.getMenuUuids().length;
        for (int index = 0; index < length; index++) {
            String menuUuid = createPedidoRequest.getMenuUuids()[index];
            BigDecimal cantidad = createPedidoRequest.getCantidades()[index];
            menu = menuService.getMenuByUuid(menuUuid);
            if (!restaurante.getIdentifier().equals(menu.getRestaurante().getIdentifier())) {
                throw new InconsistencyException("Deserialization Failed. " +
                        "menu.restaurante uuid recieved was " + menuUuid +
                        ". Expected " + createPedidoRequest.getRestauranteUuid());
            }
            menuPedido = new MenuPedido(menu, pedido, cantidad);
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
        Restaurante restaurante = restauranteService.getRestauranteByUuid(uuid);
        Pedidos pedidos = new Pedidos(SafiraUtils.toList(restaurante.getPedidos()));
        if (pedidos.getPedidos().isEmpty())
            throw new EmptyQueryResultException("No pedidos were found by the given criteria");
        return pedidos;
    }

    @Override
    public Pedidos getPedidosByUsuarioUuid(String uuid) throws EmptyQueryResultException {
        Usuario usuario = usuarioService.getUsuarioByUuid(uuid);
        Pedidos pedidos = new Pedidos(SafiraUtils.toList(usuario.getPedidos()));
        if (pedidos.getPedidos().isEmpty())
            throw new EmptyQueryResultException("No pedidos were found by the given criteria");
        return pedidos;
    }

    @Override
    public Pedidos getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid)
            throws EmptyQueryResultException {
        Usuario usuario = usuarioService.getUsuarioByUuid(resuuid);
        Restaurante restaurante = restauranteService.getRestauranteByUuid(resuuid);
        List<Pedido> pedidosByRestaurante = SafiraUtils.toList(restaurante.getPedidos());
        List<Pedido> pedidosByUsuario = SafiraUtils.toList(usuario.getPedidos());
        List<Pedido> intersection = SafiraUtils.intersection(pedidosByRestaurante, pedidosByUsuario);
        Pedidos pedidos = new Pedidos(intersection);
        if (pedidos.getPedidos().isEmpty())
            throw new EmptyQueryResultException("No pedidos were found by the given criteria");
        return pedidos;
    }
}
