package com.safira.service.deserialize;

import com.safira.common.DeserializerException;
import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;
import com.safira.service.PasswordService;

/**
 * Created by Francisco on 08/03/2015.
 */
public class RestauranteDeserializer {

    /**
     * Desired jSon format:
     * {"serializedObject":"NOMBRE;DIRECCION;TELEFONO;EMAIL;USUARIO;PASSWORD"}
     */

    private static final String FIELD_SEPARATOR = ";";
    private static final int NOMBRE = 0;
    private static final int DIRECCION = 1;
    private static final int TELEFONO = 2;
    private static final int EMAIL = 3;
    private static final int USUARIO = 4;
    private static final int PASSWORD = 5;

    private final Restaurante restaurante;
    private final RestauranteLogin restauranteLogin;

    public RestauranteDeserializer(String serializedRestaurante) throws DeserializerException {
        String[] splitFields = serializedRestaurante.split(FIELD_SEPARATOR);
        if (splitFields.length != 6) {
            throw new DeserializerException();
        }
        byte[] salt = PasswordService.getNextSalt();
        char[] password = splitFields[PASSWORD].toCharArray();
        this.restaurante = new Restaurante.Builder()
                .withNombre(splitFields[NOMBRE])
                .withDireccion(splitFields[DIRECCION])
                .withTelefono(splitFields[TELEFONO])
                .withEmail(splitFields[EMAIL])
                .build();
        this.restauranteLogin = new RestauranteLogin.Builder()
                .withUsuario(splitFields[USUARIO])
                .withSalt(salt)
                .withHashedAndSaltedPassword(PasswordService.hash(password, salt))
                .build();
        this.restauranteLogin.setId(restaurante.getId());
        this.restaurante.setRestauranteLogin(restauranteLogin);
        this.restauranteLogin.setRestaurante(restaurante);
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }
}