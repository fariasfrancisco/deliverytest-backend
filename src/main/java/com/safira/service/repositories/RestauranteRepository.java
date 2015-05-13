package com.safira.service.repositories;

import com.safira.domain.entities.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Restaurante findByUuid(String uuid);

    Page<Restaurante> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
