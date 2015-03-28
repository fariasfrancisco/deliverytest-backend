package com.safira.service.implementation;

import com.safira.api.CreatePedidoRequest;
import com.safira.common.SafiraUtils;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Pedidos;
import com.safira.domain.entities.*;
import com.safira.service.repositories.*;
import com.safira.service.Validator;
import com.safira.service.interfaces.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public Pedido createPedido(CreatePedidoRequest createPedidoRequest) throws ValidatorException, JPAQueryException, InconsistencyException {
        Validator.validatePedido(createPedidoRequest);
        Direccion direccion = direccionRepository.findByUuid(createPedidoRequest.getDireccionUuid());
        if (direccion == null) throw new JPAQueryException("Desearilization Failed. " +
                "No direccion found with uuid = " + createPedidoRequest.getDireccionUuid());
        Restaurante restaurante = restauranteRepository.findByUuid(createPedidoRequest.getRestauranteUuid());
        if (restaurante == null) throw new JPAQueryException("Desearilization Failed. " +
                "No restaurante found with uuid = " + createPedidoRequest.getRestauranteUuid());
        Usuario usuario = usuarioRepository.findByUuid(createPedidoRequest.getUsuarioUuid());
        if (usuario == null) throw new JPAQueryException("Desearilization Failed. " +
                "No usuario found with uuid = " + createPedidoRequest.getUsuarioUuid());
        Set<Menu> menus = new HashSet<>();
        Menu menu;
        for (String menuUuid : createPedidoRequest.getMenuUuids()) {
            menu = menuRepository.findByUuid(menuUuid);
            if (menu == null) throw new JPAQueryException("Desearilization Failed. " +
                    "No menu found with uuid = " + menuUuid);
            if (!restaurante.getIdentifier().equals(menu.getRestaurante().getIdentifier())) {
                System.out.println(restaurante.getIdentifier());
                System.out.println(menu.getRestaurante().getIdentifier());
                throw new InconsistencyException("Deserialization Failed. " +
                        "menu.restaurante uuid recieved was " + menuUuid +
                        ". Expected " + createPedidoRequest.getRestauranteUuid());
            }
            menus.add(menu);
        }
        Pedido pedido = new Pedido.Builder()
                .withDireccion(direccion)
                .withTelefono(createPedidoRequest.getTelefono())
                .withFecha(createPedidoRequest.getFecha())
                .withRestaurante(restaurante)
                .withUsuario(usuario)
                .withMenus(menus)
                .build();
        pedidoRepository.save(pedido);
        return pedido;
    }

    @Override
    public Pedido getPedidoByUuid(String uuid) throws JPAQueryException {
        Pedido pedido = pedidoRepository.findByUuid(uuid);
        if (pedido == null) throw new JPAQueryException("Desearilization Failed. " +
                "No pedido found with uuid = " + uuid);
        return pedido;
    }

    @Override
    public Pedidos getPedidosByRestauranteUuid(String uuid) throws JPAQueryException {
        Restaurante restaurante = restauranteRepository.findByUuid(uuid);
        if (restaurante == null) throw new JPAQueryException("Desearilization Failed. " +
                "No restaurante found with uuid = " + uuid);
        Pedidos pedidos = new Pedidos(SafiraUtils.toList(restaurante.getPedidos()));
        if (pedidos.getPedidos().isEmpty())
            throw new JPAQueryException("No pedidos were found by the given criteria");
        return pedidos;
    }

    @Override
    public Pedidos getPedidosByUsuarioUuid(String uuid) throws JPAQueryException {
        Usuario usuario = usuarioRepository.findByUuid(uuid);
        if (usuario == null) throw new JPAQueryException("Desearilization Failed. " +
                "No usuario found with uuid = " + uuid);
        Pedidos pedidos = new Pedidos(SafiraUtils.toList(usuario.getPedidos()));
        if (pedidos.getPedidos().isEmpty())
            throw new JPAQueryException("No pedidos were found by the given criteria");
        return pedidos;
    }

    @Override
    public Pedidos getPedidosByUsuarioUuidAndByRestauranteUuid(String usruuid, String resuuid)
            throws JPAQueryException {
        Usuario usuario = usuarioRepository.findByUuid(resuuid);
        if (usuario == null) throw new JPAQueryException("Desearilization Failed. " +
                "No usuario found with uuid = " + resuuid);
        Restaurante restaurante = restauranteRepository.findByUuid(resuuid);
        if (restaurante == null) throw new JPAQueryException("Desearilization Failed. " +
                "No restaurante found with uuid = " + resuuid);
        List<Pedido> pedidosByRestaurante = SafiraUtils.toList(restaurante.getPedidos());
        List<Pedido> pedidosByUsuario = SafiraUtils.toList(usuario.getPedidos());
        List<Pedido> intersection = SafiraUtils.intersection(pedidosByRestaurante, pedidosByUsuario);
        Pedidos pedidos = new Pedidos(intersection);
        if (pedidos.getPedidos().isEmpty())
            throw new JPAQueryException("No pedidos were found by the given criteria");
        return pedidos;
    }
}
