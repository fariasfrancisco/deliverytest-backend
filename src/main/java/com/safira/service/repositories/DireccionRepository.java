package com.safira.service.repositories;

import com.safira.domain.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 23/03/15.
 */
@Transactional
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    Direccion findByUuid(String uuid);
}
