package com.safira.service.repositories;

import com.safira.domain.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 23/03/15.
 */
@Transactional
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    @Query("select d from Direccion d where d.uuid = :uuid")
    Direccion findByUuid(@Param("uuid") String uuid);
}
