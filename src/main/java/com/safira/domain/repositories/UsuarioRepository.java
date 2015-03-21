package com.safira.domain.repositories;

import com.safira.domain.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 21/03/15.
 */
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario findByUuid(String uuid);
}
