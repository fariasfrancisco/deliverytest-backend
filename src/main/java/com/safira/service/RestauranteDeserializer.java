package com.safira.service;

import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;

/**
 * Created by Francisco on 08/03/2015.
 */
public class RestauranteDeserializer {

    private static final String FIELD_SEPARATOR = ":";
    private static final int NOMBRE = 0;
    private static final int DIRECCION = 1;
    private static final int TELEFONO = 2;
    private static final int EMAIL = 3;
    private static final int USUARIO = 4;
    private static final int PASSWORD = 5;

    private final String[] splitFields;
    private final Restaurante restaurante;
    private final RestauranteLogin restauranteLogin;

    public RestauranteDeserializer(String serializedRestaurante) {
        this.splitFields = serializedRestaurante.split(FIELD_SEPARATOR);
        restaurante = new Restaurante.Builder()
                .withNombre(splitFields[NOMBRE])
                .withDireccion(splitFields[DIRECCION])
                .withTelefono(splitFields[TELEFONO])
                .withEmail(splitFields[EMAIL])
                .build();
        byte[] salt = PasswordService.getNextSalt();
        char[] password = splitFields[PASSWORD].toCharArray();
        restauranteLogin = new RestauranteLogin.Builder()
                .withUsuario(splitFields[USUARIO])
                .withSalt(salt)
                .withHashedAndSaltedPassword(PasswordService.hash(password, salt))
                .build();
        restauranteLogin.setId(restaurante.getId());
        restaurante.setRestauranteLogin(restauranteLogin);
        restauranteLogin.setRestaurante(restaurante);
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }
}
