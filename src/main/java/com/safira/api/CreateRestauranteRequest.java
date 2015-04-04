package com.safira.api;

/**
 * Created by francisco on 24/03/15.
 */
public class CreateRestauranteRequest {
    private String nombre;
    private String calle;
    private String numero;
    private String telefono;
    private String email;
    private String usuario;
    private String password;

    public CreateRestauranteRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public CreateRestauranteRequest setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public CreateRestauranteRequest setCalle(String calle) {
        this.calle = calle;
        return this;
    }

    public CreateRestauranteRequest setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public CreateRestauranteRequest setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public CreateRestauranteRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateRestauranteRequest setUsuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public CreateRestauranteRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
