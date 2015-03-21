package com.safira.domain.repositories;

import com.safira.domain.entities.Restaurante;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by francisco on 21/03/15.
 */
public interface RestauranteRepository extends CrudRepository<Restaurante, Long> {
    List<Restaurante> findByNombre(String nombre);
    List<Restaurante> findByCalle(String calle);
    List<Restaurante> findByNumero(String numero);
}
