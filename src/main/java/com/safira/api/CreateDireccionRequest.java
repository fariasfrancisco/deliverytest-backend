package com.safira.api;

/**
 * Created by francisco on 24/03/15.
 */
public class CreateDireccionRequest {
    private String calle;
    private String numero;
    private String piso;
    private String departamento;
    private String usuarioUuid;

    public CreateDireccionRequest() {
    }

    public CreateDireccionRequest(String calle, String numero, String piso, String departamento, String usuarioUuid) {
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.departamento = departamento;
        this.usuarioUuid = usuarioUuid;
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

    public String getUsuarioUuid() {
        return usuarioUuid;
    }

    public CreateDireccionRequest setCalle(String calle) {
        this.calle = calle;
        return this;
    }

    public CreateDireccionRequest setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public CreateDireccionRequest setPiso(String piso) {
        this.piso = piso;
        return this;
    }

    public CreateDireccionRequest setDepartamento(String departamento) {
        this.departamento = departamento;
        return this;
    }

    public CreateDireccionRequest setUsuarioUuid(String usuarioUuid) {
        this.usuarioUuid = usuarioUuid;
        return this;
    }
}
