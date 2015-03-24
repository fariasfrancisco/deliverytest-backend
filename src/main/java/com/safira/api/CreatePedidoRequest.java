package com.safira.api;

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
    private LocalDateTime fecha;

    public CreatePedidoRequest() {
    }

    public CreatePedidoRequest(String telefono,
                               String direccionUuid,
                               String usuarioUuid,
                               String restauranteUuid,
                               String[] menuUuids,
                               LocalDateTime fecha) {
        this.telefono = telefono;
        this.direccionUuid = direccionUuid;
        this.usuarioUuid = usuarioUuid;
        this.restauranteUuid = restauranteUuid;
        this.menuUuids = menuUuids;
        this.fecha = fecha;
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

    public LocalDateTime getFecha() {
        return fecha;
    }
}
