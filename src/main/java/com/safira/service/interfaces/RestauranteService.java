package com.safira.service.interfaces;

import com.safira.api.AuthenticatedRestauranteToken;
import com.safira.api.CreateRestauranteRequest;
import com.safira.api.LoginRestauranteRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.LoginException;
import com.safira.domain.Restaurantes;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.RestauranteLogin;
import com.safira.domain.entities.RestauranteSessionToken;

/**
 * Created by francisco on 24/03/15.
 */
public interface RestauranteService {

    public Restaurante createRestaurante(CreateRestauranteRequest createRestauranteRequest);

    public AuthenticatedRestauranteToken loginRestaurante(LoginRestauranteRequest loginRestauranteRequest, ErrorOutput errorOutput) throws EmptyQueryResultException, LoginException;

    public Restaurantes getAllRestaurantes(ErrorOutput errorOutput);

    public Restaurante getRestauranteByUuid(String uuid, ErrorOutput errorOutput);

    public RestauranteSessionToken createToken(RestauranteLogin restauranteLogin);

    public void sendEmailVerification(RestauranteLogin restauranteLogin);

    public void validateEmailAddress();
}
