package com.safira.service.interfaces;

import com.safira.api.CreateUsuarioRequest;
import com.safira.common.ErrorOutput;
import com.safira.domain.entities.Usuario;

/**
 * Created by francisco on 23/03/15.
 */
public interface UsuarioService {

    public Usuario createUsuario(CreateUsuarioRequest createUsuarioRequest);

    public Usuario getUsuarioByUuid(String uuid, ErrorOutput errorOutput);
}
