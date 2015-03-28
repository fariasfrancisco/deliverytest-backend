package com.safira.service.implementation;

import com.safira.api.CreateDireccionRequest;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Direccion;
import com.safira.domain.entities.Usuario;
import com.safira.service.Validator;
import com.safira.service.interfaces.DireccionService;
import com.safira.service.repositories.DireccionRepository;
import com.safira.service.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 24/03/15.
 */
@Service("direccionService")
public class DireccionServiceImpl implements DireccionService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DireccionRepository direccionRepository;

    @Transactional
    public Direccion createDireccion(CreateDireccionRequest createDireccionRequest) throws ValidatorException, JPAQueryException {
        Validator.validateDireccion(createDireccionRequest);
        Usuario usuario = usuarioRepository.findByUuid(createDireccionRequest.getUsuarioUuid());
        if (usuario == null)
            throw new JPAQueryException("No usuario found with uuid = " + createDireccionRequest.getUsuarioUuid());
        Direccion direccion = new Direccion.Builder()
                .withUsuario(usuario)
                .withCalle(createDireccionRequest.getCalle())
                .withNumero(createDireccionRequest.getNumero())
                .withPiso(createDireccionRequest.getPiso())
                .withDepartamento(createDireccionRequest.getDepartamento())
                .build();
        direccionRepository.save(direccion);
        return direccion;
    }
}
