package com.safira.service.deserialize;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.LoginException;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import com.safira.domain.repositories.RestauranteLoginRepository;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.service.PasswordService;

/**
 * Created by francisco on 22/03/15.
 */
public class RestauranteLoginDeserializer {
    private static final String FIELD_SEPARATOR = ";";
    private static final int USERNAME = 0;
    private static final int PASSWORD = 1;

    private Restaurante restaurante;

    public RestauranteLoginDeserializer(String serializedRestauranteLogin,
                                        RestauranteLoginRepository restauranteLoginRepository,
                                        RestauranteRepository restauranteRepository)
            throws DeserializerException, JPAQueryException, LoginException {
        String[] splitFields = serializedRestauranteLogin.split(FIELD_SEPARATOR);
        if (splitFields.length != 2)
            throw new DeserializerException("Deserialization failed. " +
                    "The serializedObject recieved does not meet the length requirements.");
        char[] password = splitFields[PASSWORD].toCharArray();
        RestauranteLogin restauranteLogin = restauranteLoginRepository.findByUsuario(splitFields[USERNAME]);
        if (restauranteLogin == null) throw new JPAQueryException("Desearilization Failed. " +
                "No restaurante found with usuario = " + splitFields[USERNAME]);
        if (!PasswordService.isExpectedPassword(password, restauranteLogin.getSalt(), restauranteLogin.getHash()))
            throw new LoginException("The password recieved does not match sored password.");
        restaurante = restauranteRepository.findByUuid(restauranteLogin.getIdentifier());
        if (restaurante == null) throw new JPAQueryException("Desearilization Failed. " +
                "No restaurante found with uuid = " + restauranteLogin.getIdentifier());
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }
}
