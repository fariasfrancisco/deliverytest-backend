package com.safira.service.interfaces;

import com.safira.api.CreateDireccionRequest;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Direccion;

/**
 * Created by francisco on 06/04/15.
 */
public interface DireccionService {
    public Direccion createDireccion(CreateDireccionRequest createDireccionRequest, ErrorOutput errorOutput) throws EmptyQueryResultException;

    public Direccion getDireccionByUuid(String uuid, ErrorOutput errorOutput);
}
