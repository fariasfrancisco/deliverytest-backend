package com.safira.service.interfaces;

import com.safira.api.CreateDireccionRequest;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Direccion;

/**
 * Created by francisco on 24/03/15.
 */
public interface DireccionService {
    public Direccion createDireccion(CreateDireccionRequest createDireccionRequest) throws ValidatorException, EmptyQueryResultException;
}
