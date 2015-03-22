package com.safira.domain.repositories;

import com.safira.domain.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    @Query("select r from Restaurante r where r.uuid = :uuid")
    Restaurante findByUuid(@Param("uuid") String uuid);
}
