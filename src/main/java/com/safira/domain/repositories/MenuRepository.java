package com.safira.domain.repositories;

import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface MenuRepository extends CrudRepository<Menu, Long> {

    Menu findByUuid(String uuid);

    List<Menu> findByRestaurante(Restaurante restaurante);

    List<Menu> findByPedidos(Set<Pedido> pedido);
}
