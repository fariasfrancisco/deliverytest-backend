package com.safira.service.repositories;

import com.safira.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido findByUuid(@Param("uuid") String uuid);
}
