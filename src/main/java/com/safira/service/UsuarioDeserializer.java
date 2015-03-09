package com.safira.service;

import com.safira.entities.Usuario;

/**
 * Created by Francisco on 08/03/2015.
 */
public class UsuarioDeserializer {
    private static final String FIELD_SEPARATOR = ":";
    private static final int FACEBOOK_ID = 0;
    private static final int NOMBRE = 1;
    private static final int APELLIDO = 2;
    private static final int EMAIL = 3;

    private final String[] splitFields;
    private final Usuario usuario;

    public UsuarioDeserializer(String serializedUsuario) {
        this.splitFields = serializedUsuario.split(FIELD_SEPARATOR);
        this.usuario = new Usuario.Builder()
                .withFacebookId(splitFields[FACEBOOK_ID])
                .withNombre(splitFields[NOMBRE])
                .withApellido(splitFields[APELLIDO])
                .withEmail(splitFields[EMAIL])
                .build();
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
