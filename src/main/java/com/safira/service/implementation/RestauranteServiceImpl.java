package com.safira.service.implementation;

import com.safira.api.AuthenticatedRestauranteToken;
import com.safira.api.CreateRestauranteRequest;
import com.safira.api.LoginRestauranteRequest;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.LoginException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.common.configuration.ApplicationConfiguration;
import com.safira.domain.Restaurantes;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import com.safira.domain.entities.RestauranteSessionToken;
import com.safira.service.repositories.RestauranteLoginRepository;
import com.safira.service.repositories.RestauranteRepository;
import com.safira.service.PasswordService;
import com.safira.service.Validator;
import com.safira.service.interfaces.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 24/03/15.
 */
@Service("restauranteService")
public class RestauranteServiceImpl implements RestauranteService {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    RestauranteLoginRepository restauranteLoginRepository;

    ApplicationConfiguration applicationConfiguration;

    @Transactional
    public Restaurante createRestaurante(CreateRestauranteRequest createRestauranteRequest) throws ValidatorException {
        Validator.validateRestaurante(createRestauranteRequest);
        byte[] salt = PasswordService.getNextSalt();
        char[] password = createRestauranteRequest.getPassword().toCharArray();
        Restaurante restaurante = new Restaurante.Builder()
                .withNombre(createRestauranteRequest.getNombre())
                .withCalle(createRestauranteRequest.getCalle())
                .withNumero(createRestauranteRequest.getNumero())
                .withTelefono(createRestauranteRequest.getTelefono())
                .withEmail(createRestauranteRequest.getEmail())
                .build();
        RestauranteLogin restauranteLogin = new RestauranteLogin.Builder()
                .withUsuario(createRestauranteRequest.getUsuario())
                .withSalt(salt)
                .withHashedAndSaltedPassword(PasswordService.hash(password, salt))
                .withUuid(restaurante.getUuid())
                .withIsVerified(false)
                .build();
        restaurante.setRestauranteLogin(restauranteLogin);
        restauranteLogin.setRestaurante(restaurante);
        restauranteRepository.save(restaurante);
        restauranteLoginRepository.save(restauranteLogin);
        return restaurante;
    }

    @Transactional
    public AuthenticatedRestauranteToken loginRestaurante(LoginRestauranteRequest loginRestauranteRequest)
            throws ValidatorException, EmptyQueryResultException, LoginException {
        Validator.validateRestauranteLogin(loginRestauranteRequest);
        RestauranteLogin restauranteLogin = restauranteLoginRepository.findByUsuario(loginRestauranteRequest.getUsuario());
        if (restauranteLogin == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No restaurante found with usuario = " + loginRestauranteRequest.getUsuario());
        if (!PasswordService.isExpectedPassword(loginRestauranteRequest.getPassword().toCharArray()
                , restauranteLogin.getSalt(), restauranteLogin.getHash()))
            throw new LoginException("The password recieved does not match stored password.");
        Restaurante restaurante = restauranteLogin.getRestaurante();
        return new AuthenticatedRestauranteToken(restaurante.getIdentifier(), null);
    }

    @Transactional
    public Restaurantes getAllRestaurantes() throws EmptyQueryResultException {
        Restaurantes restaurantes;
        restaurantes = new Restaurantes(restauranteRepository.findAll());
        if (restaurantes.getRestaurantes().isEmpty())
            throw new EmptyQueryResultException("No restaurantes were found by the given criteria");
        return restaurantes;
    }

    @Transactional
    public Restaurante getRestauranteByUuid(String uuid) throws EmptyQueryResultException {
        Restaurante restaurante = restauranteRepository.findByUuid(uuid);
        if (restaurante == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No restaurante found with uuid = " + uuid);
        return restaurante;
    }

    @Override
    public RestauranteSessionToken createToken(RestauranteLogin restauranteLogin) {
        if (restauranteLogin.getRestauranteSessionToken() == null ||
                restauranteLogin.getRestauranteSessionToken().hasExpired()) {
            restauranteLogin.setRestauranteSessionToken(
                    new RestauranteSessionToken(restauranteLogin, applicationConfiguration.getSessionExpirationTime()));
            restauranteLoginRepository.save(restauranteLogin);
        }
        return restauranteLogin.getRestauranteSessionToken();
    }

    @Override
    public void sendEmailVerification(RestauranteLogin restauranteLogin) {

    }

    @Override
    public void validateEmailAddress() {

    }
}
