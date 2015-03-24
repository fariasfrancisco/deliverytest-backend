package com.safira.service.interfaces;

import com.safira.api.CreateRestauranteRequest;
import com.safira.api.LoginRestauranteRequest;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.LoginException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.Restaurantes;
import com.safira.domain.entities.Restaurante;

/**
 * Created by francisco on 24/03/15.
 */
public interface RestauranteService {

    public Restaurante createRestaurante(CreateRestauranteRequest createRestauranteRequest) throws ValidatorException;

    public Restaurante loginRestaurante(LoginRestauranteRequest loginRestauranteRequest) throws ValidatorException, JPAQueryException, LoginException;

    public Restaurantes getAllRestaurantes() throws JPAQueryException;

    public Restaurante getRestauranteByUuid(String uuid) throws JPAQueryException;
}
