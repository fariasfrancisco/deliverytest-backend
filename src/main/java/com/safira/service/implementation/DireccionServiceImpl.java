package com.safira.service.implementation;

import com.safira.api.requests.CreateDireccionRequest;
import com.safira.common.ErrorDescription;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.domain.entities.Direccion;
import com.safira.domain.entities.Usuario;
import com.safira.service.interfaces.DireccionService;
import com.safira.service.interfaces.UsuarioService;
import com.safira.service.repositories.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 06/04/15.
 */
@Service("direccionService")
public class DireccionServiceImpl implements DireccionService {
    @Autowired
    DireccionRepository direccionRepository;

    @Autowired
    UsuarioService usuarioService;

    @Transactional
    public Direccion createDireccion(CreateDireccionRequest createDireccionRequest, ErrorOutput errors)
            throws EmptyQueryResultException {
        String usuarioUuid = createDireccionRequest.getUsuarioUuid();
        Usuario usuario = usuarioService.getUsuarioByUuid(usuarioUuid, errors);
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

    @Transactional
    public Direccion getDireccionByUuid(String uuid, ErrorOutput errors) {
        Direccion direccion = direccionRepository.findByUuid(uuid);
        if (direccion == null) {
            errors.setMessage("Empty Query Result.");
            String field = "direccionUuid";
            String message = "No direccion found with uuid = " + uuid + '.';
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        return direccion;
    }
}
