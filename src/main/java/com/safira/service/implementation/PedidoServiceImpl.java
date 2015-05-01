package com.safira.service.implementation;

import com.safira.api.CreatePedidoRequest;
import com.safira.common.ErrorDescription;
import com.safira.common.ErrorOutput;
import com.safira.common.SafiraUtils;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.PedidoTimeoutException;
import com.safira.domain.entities.*;
import com.safira.service.interfaces.*;
import com.safira.service.repositories.MenuPedidoRepository;
import com.safira.service.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Pedido createPedido(CreatePedidoRequest createPedidoRequest, ErrorOutput errors)
            throws EmptyQueryResultException, InconsistencyException, PedidoTimeoutException {
        String restauranteUuid = createPedidoRequest.getRestauranteUuid();
        String usuarioUuid = createPedidoRequest.getUsuarioUuid();
        String direccionUuid = createPedidoRequest.getDireccionUuid();
        Restaurante restaurante = restauranteService.getRestauranteByUuid(restauranteUuid, errors);
        Direccion direccion = direccionService.getDireccionByUuid(usuarioUuid, errors);
        Usuario usuario = usuarioService.getUsuarioByUuid(direccionUuid, errors);
        if (errors.hasErrors()) throw new EmptyQueryResultException();
        if (createPedidoRequest.getFecha().isBefore(LocalDateTime.now().minusMinutes(2))) {
            errors.setMessage("Inconsistency Found.");
            String field = "fecha";
            String message = "The Pedido creation request took too long to reach the server.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
            throw new PedidoTimeoutException();
        }
        if (!direccion.getUsuario().equals(usuario)) {
            String field = "direccionUuid = " + direccionUuid + ", usuario = " + usuarioUuid;
            String message = "Inconsistency found with the recieved Direccion (Recieved Usuario does not match).";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        Pedido pedido = new Pedido.Builder()
                .withDireccion(direccion)
                .withTelefono(createPedidoRequest.getTelefono())
                .withFecha(createPedidoRequest.getFecha())
                .withRestaurante(restaurante)
                .withUsuario(usuario)
                .build();
        Menu menu;
        List<MenuPedido> menuPedidos = new ArrayList<>();
        int length = createPedidoRequest.getMenuUuids().length;
        for (int index = 0; index < length; index++) {
            String menuUuid = createPedidoRequest.getMenuUuids()[index];
            BigDecimal cantidad = createPedidoRequest.getCantidades()[index];
            menu = menuService.getMenuByUuid(menuUuid, errors);
            if (!restauranteUuid.equals(menu.getRestaurante().getIdentifier())) {
                errors.setMessage("Inconsistency Exception Found.");
                String field = "menuUuid = " + menuUuid + ", restaurante = " + restauranteUuid;
                String message = "Inconsistency found with the recieved Menu (Recieved Restaurante doesnot match).";
                ErrorDescription error = new ErrorDescription(field, message);
                errors.addError(error);
            }
            menuPedidos.add(new MenuPedido(menu, pedido, cantidad));
        }
        if (errors.hasErrors()) throw new InconsistencyException();
        pedidoRepository.save(pedido);
        menuPedidos.forEach(menuPedidoRepository::save);
        return pedido;
    }

    @Transactional
    public Pedido getPedidoByUuid(String uuid, ErrorOutput errors) {
        Pedido pedido = pedidoRepository.findByUuid(uuid);
        if (pedido == null) {
            errors.setMessage("Empty Query Result.");
            String field = "pedidoUuid";
            String message = "No pedido found with uuid = " + uuid + '.';
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return pedido;
    }

    @Transactional
    public List<Pedido> getPedidosByRestauranteUuid(String uuid, ErrorOutput errors) {
        List<Pedido> pedidos = new ArrayList<>();
        Restaurante restaurante = restauranteService.getRestauranteByUuid(uuid, errors);
        if (errors.hasErrors()) return pedidos;
        pedidos = SafiraUtils.toList(restaurante.getPedidos());
        if (pedidos.isEmpty()) {
            errors.setMessage("Empty Query Result.");
            String field = "restauranteUuid";
            String message = "No pedidos were found by the given criteria.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return pedidos;
    }

    @Transactional
    public List<Pedido> getPedidosByUsuarioUuid(String uuid, ErrorOutput errors) {
        List<Pedido> pedidos = new ArrayList<>();
        Usuario usuario = usuarioService.getUsuarioByUuid(uuid, errors);
        if (errors.hasErrors()) return pedidos;
        pedidos = SafiraUtils.toList(usuario.getPedidos());
        if (pedidos.isEmpty()) {
            errors.setMessage("Empty Query Result.");
            String field = "restauranteUuid";
            String message = "No pedidos were found by the given criteria.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return pedidos;
    }

    @Transactional
    public List<Pedido> getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid, ErrorOutput errors) {
        List<Pedido> pedidos = new ArrayList<>();
        Usuario usuario = usuarioService.getUsuarioByUuid(resuuid, errors);
        Restaurante restaurante = restauranteService.getRestauranteByUuid(resuuid, errors);
        if (errors.hasErrors()) return pedidos;
        List<Pedido> pedidosByRestaurante = SafiraUtils.toList(restaurante.getPedidos());
        List<Pedido> pedidosByUsuario = SafiraUtils.toList(usuario.getPedidos());
        pedidos = SafiraUtils.intersection(pedidosByRestaurante, pedidosByUsuario);
        if (pedidos.isEmpty()) {
            errors.setMessage("Empty Query Result.");
            String field = "restauranteUuid, usuarioUuid";
            String message = "No pedidos were found by the given criteria.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return pedidos;
    }
}
