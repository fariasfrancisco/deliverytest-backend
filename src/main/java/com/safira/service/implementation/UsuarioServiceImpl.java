package com.safira.service.implementation;

import com.safira.api.CreateDireccionRequest;
import com.safira.api.CreateUsuarioRequest;
import com.safira.api.LoginUsuarioRequest;
import com.safira.common.exceptions.EmptyQueryResultException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Direccion;
import com.safira.domain.entities.Usuario;
import com.safira.service.repositories.DireccionRepository;
import com.safira.service.repositories.UsuarioRepository;
import com.safira.service.Validator;
import com.safira.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by francisco on 24/03/15.
 */
@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DireccionRepository direccionRepository;

    @Transactional
    public Usuario createUsuario(CreateUsuarioRequest createUsuarioRequest) throws ValidatorException {
        Validator.validateUsuario(createUsuarioRequest);
        Usuario usuario = new Usuario.Builder()
                .withFacebookId(createUsuarioRequest.getFacebookId())
                .withNombre(createUsuarioRequest.getNombre())
                .withApellido(createUsuarioRequest.getApellido())
                .withEmail(createUsuarioRequest.getEmail())
                .build();
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Transactional
    public Usuario loginUsuario(LoginUsuarioRequest loginUsuarioRequest) {
        return null;
    }


    @Transactional
    public Usuario getUsuarioByUuid(String uuid) throws EmptyQueryResultException {
        Usuario usuario = usuarioRepository.findByUuid(uuid);
        if (usuario == null) throw new EmptyQueryResultException("Desearilization Failed. " +
                "No usuario found with uuid = " + uuid);
        return usuario;
    }

    @Override
    public Direccion createDireccion(CreateDireccionRequest createDireccionRequest) throws ValidatorException, EmptyQueryResultException {
        Validator.validateDireccion(createDireccionRequest);
        Usuario usuario = getUsuarioByUuid(createDireccionRequest.getUsuarioUuid());
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
}
