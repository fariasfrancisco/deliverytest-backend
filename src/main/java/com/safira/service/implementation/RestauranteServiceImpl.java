package com.safira.service.implementation;

import com.safira.api.requests.CreateRestauranteRequest;
import com.safira.api.requests.LoginRestauranteRequest;
import com.safira.api.responses.AuthenticatedRestauranteToken;
import com.safira.api.responses.TokenVerificationResult;
import com.safira.common.ErrorDescription;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.LoginException;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import com.safira.domain.entities.RestauranteSessionToken;
import com.safira.service.PasswordService;
import com.safira.service.interfaces.RestauranteService;
import com.safira.service.repositories.RestauranteLoginRepository;
import com.safira.service.repositories.RestauranteRepository;
import com.safira.service.repositories.RestauranteSessionTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by francisco on 24/03/15.
 */
@Service("restauranteService")
public class RestauranteServiceImpl implements RestauranteService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    RestauranteSessionTokenRepository restauranteSessionTokenRepository;

    @Autowired
    RestauranteLoginRepository restauranteLoginRepository;

    @Transactional
    public Restaurante createRestaurante(CreateRestauranteRequest createRestauranteRequest) {
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
    public AuthenticatedRestauranteToken loginRestaurante(LoginRestauranteRequest loginRestauranteRequest, ErrorOutput errors)
            throws EmptyQueryResultException, LoginException {
        String usuarioUuid = loginRestauranteRequest.getUsuario();
        RestauranteLogin restauranteLogin = restauranteLoginRepository.findByUsuario(usuarioUuid);
        if (restauranteLogin == null) {
            errors.setMessage("Empty Query Exception.");
            String field = "usuarioUuid";
            String message = "No restaurante found with usuario = " + usuarioUuid + '.';
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
            throw new EmptyQueryResultException();
        }
        char[] password = loginRestauranteRequest.getPassword().toCharArray();
        byte[] salt = restauranteLogin.getSalt();
        byte[] hash = restauranteLogin.getHash();
        if (!PasswordService.isExpectedPassword(password, salt, hash)) {
            errors.setMessage("Authentication Failure.");
            String field = "password";
            String message = "The recieved username or password does not match.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
            throw new LoginException();
        }
        RestauranteSessionToken restauranteSessionToken = createToken(restauranteLogin);
        String uuid = restauranteLogin.getUuid();
        String token = restauranteSessionToken.getToken();
        return new AuthenticatedRestauranteToken(uuid, token);
    }

    @Transactional
    public List<Restaurante> getAllRestaurantes(int pageNumber, ErrorOutput errors) {
        Pageable pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "nombre");
        Page<Restaurante> queryPage = restauranteRepository.findAll(pageRequest);
        if (queryPage.getNumberOfElements() == 0) {
            errors.setMessage("Empty Query Exception.");
            String field = "N/A";
            String message = "The page for the query returned no Restaurantes.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        List<Restaurante> restaurantes = queryPage.getContent();
        return restaurantes;
    }

    @Transactional
    public Restaurante getRestauranteByUuid(String uuid, ErrorOutput errors) {
        Restaurante restaurante = restauranteRepository.findByUuid(uuid);
        if (restaurante == null) {
            errors.setMessage("Empty Query Result.");
            String field = "restauranteUuid";
            String message = "No restaurante found with uuid = " + uuid + '.';
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return restaurante;
    }

    @Transactional
    public List<Restaurante> getRestaurantesByNombre(String nombre, int pageNumber, ErrorOutput errors) {
        Pageable pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "nombre");
        Page<Restaurante> queryPage = restauranteRepository.findByNombreContainingIgnoreCase(nombre, pageRequest);
        if (queryPage.getNumberOfElements() == 0) {
            errors.setMessage("Empty Query Exception.");
            String field = "N/A";
            String message = "The page for the query returned no Restaurantes.";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return queryPage.getContent();
    }

    @Transactional
    public TokenVerificationResult verififyAuthenticationToken(AuthenticatedRestauranteToken authenticatedRestauranteToken, ErrorOutput errorOutput) {
        String restauranteUuid = authenticatedRestauranteToken.getRestauranteUuid();
        String token = authenticatedRestauranteToken.getToken();
        RestauranteLogin restauranteLogin = restauranteLoginRepository.findByUuid(restauranteUuid);
        RestauranteSessionToken restauranteSessionToken = restauranteLogin.getRestauranteSessionToken();
        boolean result = !(restauranteSessionToken == null ||
                !Objects.equals(restauranteSessionToken.getToken(), token) ||
                restauranteSessionToken.hasExpired());
        return new TokenVerificationResult(result);
    }

    @Transactional
    private RestauranteSessionToken createToken(RestauranteLogin restauranteLogin) {
        RestauranteSessionToken restauranteSessionToken = restauranteLogin.getRestauranteSessionToken();
        if (restauranteSessionToken == null || restauranteSessionToken.hasExpired()) {
            restauranteLogin.setRestauranteSessionToken(
                    new RestauranteSessionToken(restauranteLogin, 720));
            restauranteSessionTokenRepository.save(restauranteSessionToken);
        }
        return restauranteLogin.getRestauranteSessionToken();
    }

    @Transactional
    public void sendEmailVerification(RestauranteLogin restauranteLogin) {

    }

    @Transactional
    public void validateEmailAddress() {

    }
}
