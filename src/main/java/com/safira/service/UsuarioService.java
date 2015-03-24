package com.safira.service;

import com.safira.domain.entities.Direccion;
import com.safira.domain.entities.Favoritos;
import com.safira.domain.entities.Usuario;

/**
 * Created by francisco on 23/03/15.
 */
public interface UsuarioService {

    public Usuario createUsuario();

    public Usuario loginUsuario();

    public Direccion addDireccion();

    public Favoritos addPedidoToFavoritos();
}
