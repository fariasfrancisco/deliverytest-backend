package com.safira.api;

/**
 * Created by francisco on 24/03/15.
 */
public class CreateUsuarioRequest {
    private String facebookId;
    private String nombre;
    private String apellido;
    private String email;

    public CreateUsuarioRequest() {
    }

    public CreateUsuarioRequest(String facebookId, String nombre, String apellido, String email) {
        this.facebookId = facebookId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public CreateUsuarioRequest setFacebookId(String facebookId) {
        this.facebookId = facebookId;
        return this;
    }

    public CreateUsuarioRequest setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public CreateUsuarioRequest setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public CreateUsuarioRequest setEmail(String email) {
        this.email = email;
        return this;
    }
}
