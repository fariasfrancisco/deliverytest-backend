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

    public CreateMenuRequest setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public CreateMenuRequest setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public CreateMenuRequest setCosto(BigDecimal costo) {
        this.costo = costo;
        return this;
    }

    public CreateMenuRequest setRestauranteUuid(String restauranteUuid) {
        this.restauranteUuid = restauranteUuid;
        return this;
    }
}
