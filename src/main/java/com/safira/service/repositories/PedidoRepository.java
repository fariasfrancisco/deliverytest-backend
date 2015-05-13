package com.safira.service.repositories;

import com.safira.domain.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido findByUuid(String uuid);

    Page<Pedido> findByRestaurante_Uuid(String uuid, Pageable pageable);

    Page<Pedido> findByUsuario_Uuid(String uuid, Pageable pageable);

    Page<Pedido> findByUsuario_UuidAndRestaurante_Uuid(String usuarioUuid, String restauranteUuid, Pageable pageable);
}
