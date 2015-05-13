package com.safira.api.requests;

/**
 * Created by francisco on 24/03/15.
 */
public class LoginRestauranteRequest {
    private String usuario;
    private String password;

    public LoginRestauranteRequest() {
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }
}
