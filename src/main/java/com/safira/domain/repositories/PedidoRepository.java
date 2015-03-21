package com.safira.domain.repositories;

import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface PedidoRepository extends CrudRepository<Pedido, Long> {
    Pedido findByUuid(String uuid);

    List<Pedido> findByRestaurante(Restaurante restaurante);

    List<Pedido> findByUsuario(Usuario usuario);

    List<Pedido> findByUsuarioAndRestaurante(Usuario usuario, Restaurante restaurante);
}
