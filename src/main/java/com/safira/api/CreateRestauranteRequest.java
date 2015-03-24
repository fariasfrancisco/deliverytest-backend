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

    public CreateRestauranteRequest(String nombre,
                                    String calle,
                                    String numero,
                                    String telefono,
                                    String email,
                                    String usuario,
                                    String password) {
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
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
}
