package com.safira.service.implementation;

import com.safira.api.CreateUsuarioRequest;
import com.safira.api.LoginUsuarioRequest;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Usuario;
import com.safira.domain.repositories.UsuarioRepository;
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
    public Usuario getUsuarioByUuid(String uuid) throws JPAQueryException {
        Usuario usuario = usuarioRepository.findByUuid(uuid);
        if (usuario == null) throw new JPAQueryException("Desearilization Failed. " +
                "No usuario found with uuid = " + uuid);
        return usuario;
    }
}
