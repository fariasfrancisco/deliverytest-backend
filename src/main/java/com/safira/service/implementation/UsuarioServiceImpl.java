package com.safira.service.implementation;

import com.safira.api.requests.CreateUsuarioRequest;
import com.safira.common.ErrorOutput;
import com.safira.domain.entities.Usuario;
import com.safira.service.interfaces.UsuarioService;
import com.safira.service.repositories.UsuarioRepository;
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
    public Usuario createUsuario(CreateUsuarioRequest createUsuarioRequest) {
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
    public Usuario getUsuarioByUuid(String uuid, ErrorOutput errors) {
        Usuario usuario = usuarioRepository.findByUuid(uuid);
        if (usuario == null)
            errors.addError("Empty Query Exception.", "usuarioUuid", "No usuario found with uuid = " + uuid + '.');
        return usuario;
    }
}
