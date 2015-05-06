package com.safira.service.interfaces;

import com.safira.api.responses.AuthenticatedRestauranteToken;
import com.safira.api.requests.CreateRestauranteRequest;
import com.safira.api.requests.LoginRestauranteRequest;
import com.safira.api.responses.TokenVerificationResult;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.LoginException;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;

import java.util.List;

/**
 * Created by francisco on 24/03/15.
 */
public interface RestauranteService {

    public Restaurante createRestaurante(CreateRestauranteRequest createRestauranteRequest);

    public AuthenticatedRestauranteToken loginRestaurante(LoginRestauranteRequest loginRestauranteRequest, ErrorOutput errorOutput) throws EmptyQueryResultException, LoginException;

    public List<Restaurante> getAllRestaurantes(ErrorOutput errorOutput);

    public Restaurante getRestauranteByUuid(String uuid, ErrorOutput errorOutput);

    public TokenVerificationResult verififyAuthenticationToken(AuthenticatedRestauranteToken authenticatedRestauranteToken, ErrorOutput errorOutput);

    public void sendEmailVerification(RestauranteLogin restauranteLogin);

    public void validateEmailAddress();
}
