package com.safira.service.interfaces;

import com.safira.api.CreateDireccionRequest;
import com.safira.api.CreateUsuarioRequest;
import com.safira.api.LoginUsuarioRequest;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Direccion;
import com.safira.domain.entities.Usuario;

/**
 * Created by francisco on 23/03/15.
 */
public interface UsuarioService {

    public Usuario createUsuario(CreateUsuarioRequest createUsuarioRequest) throws ValidatorException;

    public Usuario loginUsuario(LoginUsuarioRequest loginUsuarioRequest);

    public Usuario getUsuarioByUuid(String uuid) throws EmptyQueryResultException;
}
