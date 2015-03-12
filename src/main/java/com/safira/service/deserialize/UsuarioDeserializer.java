package com.safira.service.deserialize;

import com.safira.common.DeserializerException;
import com.safira.entities.Usuario;

/**
 * Created by Francisco on 08/03/2015.
 */
public class UsuarioDeserializer {

    /**
     * Desired jSon format:
     * {"serializedObject":"FACEBOOK_ID;NOMBRE;APELLIDO;EMAIL"}
     */

    private static final String FIELD_SEPARATOR = ";";
    private static final int FACEBOOK_ID = 0;
    private static final int NOMBRE = 1;
    private static final int APELLIDO = 2;
    private static final int EMAIL = 3;

    private final Usuario usuario;

    public UsuarioDeserializer(String serializedUsuario) throws DeserializerException {
        String[] splitFields = serializedUsuario.split(FIELD_SEPARATOR);
        if(splitFields.length != 4){
            throw new DeserializerException();
        }
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