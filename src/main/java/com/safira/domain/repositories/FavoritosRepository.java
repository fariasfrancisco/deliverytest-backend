package com.safira.domain.repositories;

import com.safira.domain.entities.Favoritos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 24/03/15.
 */@Transactional
public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {
}
