package com.safira.service.interfaces;

import com.safira.api.requests.CreateRestauranteRequest;
import com.safira.api.requests.LoginRestauranteRequest;
import com.safira.api.responses.AuthenticatedRestauranteToken;
import com.safira.api.responses.TokenVerificationResult;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.LoginException;
import com.safira.domain.entities.Restaurante;

import java.util.List;

/**
 * Created by francisco on 24/03/15.
 */
public interface RestauranteService {

    Restaurante createRestaurante(CreateRestauranteRequest createRestauranteRequest);

    AuthenticatedRestauranteToken loginRestaurante(LoginRestauranteRequest loginRestauranteRequest, ErrorOutput errorOutput) throws EmptyQueryResultException, LoginException;

    List<Restaurante> getAllRestaurantes(int pageNumber, ErrorOutput errorOutput);

    Restaurante getRestauranteByUuid(String uuid, ErrorOutput errorOutput);

    List<Restaurante> getRestaurantesByNombre(String nombre, int pageNumber, ErrorOutput errorOutput);

    TokenVerificationResult verififyAuthenticationToken(AuthenticatedRestauranteToken authenticatedRestauranteToken, ErrorOutput errorOutput);
}
