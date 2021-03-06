package com.safira.service.repositories;

import com.safira.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUuid(String uuid);
}
