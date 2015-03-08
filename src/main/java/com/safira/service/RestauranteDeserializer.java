package com.safira.service;

import com.safira.entities.Restaurante;
import com.safira.entities.RestauranteLogin;

/**
 * Created by Francisco on 08/03/2015.
 */
public class RestauranteDeserializer {

    private static final int NOMBRE = 0;
    private static final int DIRECCION = 1;
    private static final int TELEFONO = 2;
    private static final int EMAIL = 3;
    private static final int USUARIO = 4;
    private static final int PASSWORD = 5;
    private String[] separatedFields;
    private Restaurante restaurante;
    private RestauranteLogin restauranteLogin;

    public RestauranteDeserializer(String serializedRestaurante) {
        this.separatedFields = serializedRestaurante.split(":");
        restaurante = this.deserializeRestaurante();
        restauranteLogin = this.deserializeRestauranteLogin();
        restaurante.setRestauranteLogin(restauranteLogin);
        restauranteLogin.setRestaurante(restaurante);
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public RestauranteLogin getRestauranteLogin() {
        return restauranteLogin;
    }

    public Restaurante deserializeRestaurante() {
        return new Restaurante.Builder()
                .withNombre(separatedFields[NOMBRE])
                .withDireccion(separatedFields[DIRECCION])
                .withTelefono(separatedFields[TELEFONO])
                .withEmail(separatedFields[EMAIL])
                .build();

    }

    public RestauranteLogin deserializeRestauranteLogin() {
        byte[] salt = PasswordService.getNextSalt();
        char[] password = separatedFields[PASSWORD].toCharArray();
        return new RestauranteLogin.Builder()
                .withUserName(separatedFields[USUARIO])
                .withSalt(salt)
                .withHashedAndSaltedPassword(PasswordService.hash(password, salt))
                .build();
    }
}
