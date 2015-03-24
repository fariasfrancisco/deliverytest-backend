package com.safira.api;

/**
 * Created by francisco on 24/03/15.
 */
public class LoginRestauranteRequest {
    private String usuario;
    private String password;

    public LoginRestauranteRequest() {
    }

    public LoginRestauranteRequest(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }
}
