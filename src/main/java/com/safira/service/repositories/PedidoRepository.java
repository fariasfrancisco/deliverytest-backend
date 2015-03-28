package com.safira.service.repositories;

import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("select p from Pedido p where p.uuid = :uuid")
    Pedido findByUuid(@Param("uuid") String uuid);

    List<Pedido> findByRestaurante(Restaurante restaurante);

    List<Pedido> findByUsuario(Usuario usuario);

    List<Pedido> findByUsuarioAndRestaurante(Usuario usuario, Restaurante restaurante);
}
