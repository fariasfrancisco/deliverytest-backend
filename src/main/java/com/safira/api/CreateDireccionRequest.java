package com.safira.api;

/**
 * Created by francisco on 24/03/15.
 */
public class CreateDireccionRequest {
    private String calle;
    private String numero;
    private String piso;
    private String departamento;

    public CreateDireccionRequest() {
    }

    public CreateDireccionRequest(String calle, String numero, String piso, String departamento) {
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.departamento = departamento;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getPiso() {
        return piso;
    }

    public String getDepartamento() {
        return departamento;
    }
}
