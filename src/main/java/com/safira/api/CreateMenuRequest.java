package com.safira.api;

import java.math.BigDecimal;

/**
 * Created by francisco on 24/03/15.
 */
public class CreateMenuRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal costo;
    private String restauranteUuid;

    public CreateMenuRequest() {
    }

    public CreateMenuRequest(String nombre, String descripcion, BigDecimal costo, String restauranteUuid) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.restauranteUuid = restauranteUuid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public String getRestauranteUuid() {
        return restauranteUuid;
    }
}
