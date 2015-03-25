package com.safira.domain.repositories;

import com.safira.domain.entities.RestauranteSessionToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 25/03/15.
 */
@Transactional
public interface RestauranteSessionTokenRepository extends CrudRepository<RestauranteSessionToken, Long> {
}
