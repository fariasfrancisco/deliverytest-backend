package com.safira.domain.repositories;

import com.safira.domain.entities.Restaurante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface RestauranteRepository extends CrudRepository<Restaurante, Long> {
    Restaurante findByUuid(String uuid);
}
