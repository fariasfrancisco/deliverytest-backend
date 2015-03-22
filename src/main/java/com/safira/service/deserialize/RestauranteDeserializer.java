package com.safira.service.deserialize;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import com.safira.service.PasswordService;
import com.safira.service.Validator;

/**
 * Class in charge of deserilizing recieved String and create Restaurante and RestauranteLogin objects out of it.
 */
public class RestauranteDeserializer {

    /**
     * Desired jSon format:
     * {"serializedObject":"NOMBRE;CALLE;NUMERO;TELEFONO;EMAIL;USUARIO;PASSWORD"}
     */

    private static final String FIELD_SEPARATOR = ";";
    private static final int NOMBRE = 0;
    private static final int CALLE = 1;
    private static final int NUMERO = 2;
    private static final int TELEFONO = 3;
    private static final int EMAIL = 4;
    private static final int USUARIO = 5;
    private static final int PASSWORD = 6;

    private final Restaurante restaurante;
    private final RestauranteLogin restauranteLogin;

    public RestauranteDeserializer(String serializedRestaurante) throws DeserializerException, ValidatorException {
        String[] splitFields = serializedRestaurante.split(FIELD_SEPARATOR);
        if (splitFields.length != 7) {
            throw new DeserializerException("The serializedObject recieved does not meet the length requirements.");
        }
        Validator.validateRestaurante(splitFields[USUARIO],
                splitFields[PASSWORD],
                splitFields[NUMERO],
                splitFields[TELEFONO],
                splitFields[EMAIL]);
        byte[] salt = PasswordService.getNextSalt();
        char[] password = splitFields[PASSWORD].toCharArray();
        this.restaurante = new Restaurante.Builder()
                .withNombre(splitFields[NOMBRE])
                .withCalle(splitFields[CALLE])
                .withNumero(splitFields[NUMERO])
                .withTelefono(splitFields[TELEFONO])
                .withEmail(splitFields[EMAIL])
                .build();
        this.restauranteLogin = new RestauranteLogin.Builder()
                .withUsuario(splitFields[USUARIO])
                .withSalt(salt)
                .withHashedAndSaltedPassword(PasswordService.hash(password, salt))
                .withVerificado(false)
                .build();
        this.restauranteLogin.setUuid(restaurante.getIdentifier());
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
