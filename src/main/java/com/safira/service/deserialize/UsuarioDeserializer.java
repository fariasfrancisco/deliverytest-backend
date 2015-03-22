package com.safira.service.deserialize;

import com.safira.common.exceptions.DeserializerException;
import com.safira.domain.entities.Usuario;
import com.safira.service.Validator;

/**
 * Class in charge of deserilizing recieved String and create an Usuario object out of it.
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
        if (splitFields.length != 4) {
            throw new DeserializerException();
        }
        Validator.validateUsuario(splitFields[EMAIL]);
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
