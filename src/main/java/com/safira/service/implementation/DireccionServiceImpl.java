package com.safira.service.implementation;

import com.safira.api.CreateDireccionRequest;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Direccion;
import com.safira.domain.entities.Usuario;
import com.safira.service.Validator;
import com.safira.service.interfaces.DireccionService;
import com.safira.service.interfaces.UsuarioService;
import com.safira.service.repositories.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by francisco on 06/04/15.
 */
public class DireccionServiceImpl implements DireccionService {
    @Autowired
    DireccionRepository direccionRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public Direccion createDireccion(CreateDireccionRequest createDireccionRequest) throws ValidatorException, EmptyQueryResultException {
        Validator.validateDireccion(createDireccionRequest);
        Usuario usuario = usuarioService.getUsuarioByUuid(createDireccionRequest.getUsuarioUuid());
        Direccion direccion = new Direccion.Builder()
                .withCalle(createDireccionRequest.getCalle())
                .withNumero(createDireccionRequest.getNumero())
                .withPiso(createDireccionRequest.getPiso())
                .withDepartamento(createDireccionRequest.getDepartamento())
                .withUsuario(usuario)
                .build();
        direccionRepository.save(direccion);
        return direccion;
    }

    @Override
    public Direccion getDireccionByUuid(String uuid) throws EmptyQueryResultException {
        Direccion direccion = direccionRepository.findByUuid(uuid);
        if (direccion == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No direccion found with uuid = " + uuid);
        return direccion;
    }
}
