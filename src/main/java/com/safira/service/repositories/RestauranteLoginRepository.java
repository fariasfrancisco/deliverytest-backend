package com.safira.service.repositories;

import com.safira.domain.entities.RestauranteLogin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface RestauranteLoginRepository extends CrudRepository<RestauranteLogin, Long> {
    RestauranteLogin findByUsuario(String usuario);

    RestauranteLogin findByUuid(@Param("uuid") String uuid);
}
