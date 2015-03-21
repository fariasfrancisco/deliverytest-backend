package com.safira.domain.repositories;

import com.safira.domain.entities.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by francisco on 21/03/15.
 */
public interface MenuRepository extends CrudRepository<Menu, Long> {
    List<Menu> findByNombre(String nombre);
    List<Menu> findByCalle(String calle);
    List<Menu> findByNumero(String numero);
}