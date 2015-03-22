package com.safira.domain.repositories;

import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m where m.uuid = :uuid")
    Menu findByUuid(@Param("uuid") String uuid);

    List<Menu> findByRestaurante(Restaurante restaurante);

    List<Menu> findByPedidos(Set<Pedido> pedido);
}
