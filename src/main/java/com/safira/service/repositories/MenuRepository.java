package com.safira.service.repositories;

import com.safira.domain.entities.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByUuid(String uuid);

    Page<Menu> findByRestaurante_Uuid(String uuid, Pageable pageable);
}
