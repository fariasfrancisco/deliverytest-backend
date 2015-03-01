package com.safira.domain;

import com.safira.entities.Usuario;

import java.util.List;

/**
 * Created by francisco on 01/03/15.
 */
public class Usuarios {
    private List<Usuario> usuarios;

    public Usuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario get(int i) {
        return usuarios.get(i);
    }

    @Override
    public String toString() {
        String s = "Usuarios{";
        for (Usuario u : usuarios) {
            s += u.toString();
        }
        s += '}';
        return s;
    }
}
