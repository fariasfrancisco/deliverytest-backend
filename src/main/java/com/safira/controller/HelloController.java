package com.safira.controller;


import com.safira.domain.Usuarios;
import com.safira.entities.Usuario;
import com.safira.service.QueryService;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
public class HelloController {

    private Facebook facebook;

    @Inject
    public HelloController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (!facebook.isAuthorized()) {
            return "redirect:/connect/facebook";
        }
        FacebookProfile facebookProfile = facebook.userOperations().getUserProfile();
        QueryService queryService = new QueryService();
        Usuarios usuarios = new Usuarios(queryService.GetUsuario(facebookProfile.getId()));
        System.out.println(usuarios.getUsuarios().size());
        if (usuarios.getUsuarios().isEmpty()) {
            String email = "";
            String facebookId = facebookProfile.getId();
            String nombre = facebookProfile.getFirstName();
            String apellido = facebookProfile.getLastName();
            if (facebookProfile.getEmail() == null) {
                if (facebookProfile.getUsername() != null) {
                    email = facebookProfile.getUsername() + "@facebook.com";
                } else {
                    email = "no@mail.com";
                }
            }
            Usuario usuario = new Usuario.Builder()
                    .withFacebookId(facebookId)
                    .withNombre(nombre)
                    .withApellido(apellido)
                    .withEmail(email)
                    .build();
            queryService.InsertObject(usuario);
        }
        model.addAttribute(facebook.userOperations().getUserProfile());
        PagedList<Post> homeFeed = facebook.feedOperations().getHomeFeed();
        model.addAttribute("feed", homeFeed);

        return "hello";
    }

}