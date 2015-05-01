package com.safira.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.safira.domain.LocalDateTimeDeserializer;
import com.safira.domain.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by francisco on 24/03/15.
 */
public class CreatePedidoRequest {
    private String telefono;
    private String direccionUuid;
    private String usuarioUuid;
    private String restauranteUuid;
    private String[] menuUuids;
    private BigDecimal[] cantidades;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime fecha;

    public CreatePedidoRequest() {
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccionUuid() {
        return direccionUuid;
    }

    public String getUsuarioUuid() {
        return usuarioUuid;
    }

    public String getRestauranteUuid() {
        return restauranteUuid;
    }

    public String[] getMenuUuids() {
        return menuUuids;
    }

    public BigDecimal[] getCantidades() {
        return cantidades;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public CreatePedidoRequest setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public CreatePedidoRequest setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public CreatePedidoRequest setDireccionUuid(String direccionUuid) {
        this.direccionUuid = direccionUuid;
        return this;
    }

    public CreatePedidoRequest setUsuarioUuid(String usuarioUuid) {
        this.usuarioUuid = usuarioUuid;
        return this;
    }

    public CreatePedidoRequest setRestauranteUuid(String restauranteUuid) {
        this.restauranteUuid = restauranteUuid;
        return this;
    }

    public CreatePedidoRequest setMenuUuids(String[] menuUuids) {
        this.menuUuids = menuUuids;
        return this;
    }

    public CreatePedidoRequest setCantidades(BigDecimal[] cantidades) {
        this.cantidades = cantidades;
        return this;
    }
}
