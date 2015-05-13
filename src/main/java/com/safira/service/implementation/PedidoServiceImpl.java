package com.safira.service.implementation;

import com.safira.api.requests.CreatePedidoRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.PedidoTimeoutException;
import com.safira.domain.entities.*;
import com.safira.service.interfaces.*;
import com.safira.service.repositories.MenuPedidoRepository;
import com.safira.service.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private static final int PAGE_SIZE = 10;

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
            errors.addError("Inconsistency Found.", "fecha",
                    "The Pedido creation request took too long to reach the server.");
            throw new PedidoTimeoutException();
        }
        if (!direccion.getUsuario().equals(usuario))
            errors.addError("Inconsistency Found.",
                    "direccionUuid = " + direccionUuid + ", usuario = " + usuarioUuid,
                    "Inconsistency found with the recieved Direccion (Recieved Usuario does not match).");
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
            if (!restauranteUuid.equals(menu.getRestaurante().getUuid()))
                errors.addError("Inconsistency Exception Found.",
                        "menuUuid = " + menuUuid + ", restaurante = " + restauranteUuid,
                        "Inconsistency found with the recieved Menu (Recieved Restaurante doesnot match).");
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
        if (pedido == null)
            errors.addError("Empty Query Result.", "pedidoUuid", "No pedido found with uuid = " + uuid + '.');
        return pedido;
    }

    @Transactional
    public List<Pedido> getPedidosByRestauranteUuid(String uuid, int pageNumber, ErrorOutput errors) {
        Pageable pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "fecha");
        Page<Pedido> queryPage = pedidoRepository.findByRestaurante_Uuid(uuid, pageRequest);
        if (queryPage.getNumberOfElements() == 0)
            errors.addError("Empty Query Exception.", "N/A", "The page for the query returned no Pedidos.");
        return queryPage.getContent();
    }

    @Transactional
    public List<Pedido> getPedidosByUsuarioUuid(String uuid, int pageNumber, ErrorOutput errors) {
        Pageable pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "fecha");
        Page<Pedido> queryPage = pedidoRepository.findByUsuario_Uuid(uuid, pageRequest);
        if (queryPage.getNumberOfElements() == 0)
            errors.addError("Empty Query Exception.", "N/A", "The page for the query returned no Pedidos.");
        return queryPage.getContent();
    }

    @Transactional
    public List<Pedido> getPedidosByUsuarioUuidAndByRestauranteUuid(String usuarioUuid,
                                                                    String restauranteUuid,
                                                                    int pageNumber,
                                                                    ErrorOutput errors) {
        Pageable pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "fecha");
        Page<Pedido> queryPage = pedidoRepository.findByUsuario_UuidAndRestaurante_Uuid(usuarioUuid, restauranteUuid, pageRequest);
        if (queryPage.getNumberOfElements() == 0)
            errors.addError("Empty Query Exception.", "N/A", "The page for the query returned no Pedidos.");
        return queryPage.getContent();
    }
}
