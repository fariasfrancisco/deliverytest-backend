package com.safira.service.deserialize;

import com.safira.common.exceptions.DeserializerException;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.Usuario;
import com.safira.service.Validator;
import com.safira.service.hibernate.QueryService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Class in charge of deserilizing recieved String and create a Pedido object out of it.
 */
public class PedidoDeserializer {

    /**
     * Desired jSon format:
     * {"serializedObject":"CALLE;NUMERO;PISO;DEPARTARMENTO;TELEFONO;USUARIO_UUID;RESTAURANTE_ID;MENU_UUID&...&MENU_UUID"}
     */

    private static final String FIELD_SEPARATOR = ";";
    private static final String MENU_SEPARATOR = "&";
    private static final int CALLE = 0;
    private static final int NUMERO = 1;
    private static final int PISO = 2;
    private static final int DEPARTARMENTO = 3;
    private static final int TELEFONO = 4;
    private static final int USUARIO_UUID = 5;
    private static final int RESTAURANTE_UUID = 6;
    private static final int MENUS = 7;

    private Pedido pedido;

    public PedidoDeserializer(String serializedPedido, QueryService queryService) throws DeserializerException {
        Restaurante restaurante;
        Usuario usuario;
        Set<Menu> menus = new HashSet<>();
        String[] splitFields = serializedPedido.split(FIELD_SEPARATOR);
        if (splitFields.length < 8) {
            throw new DeserializerException();
        }
        String[] splitMenus = splitFields[MENUS].split(MENU_SEPARATOR);
        if (!Validator.validatePedido(splitFields[NUMERO],
                splitFields[TELEFONO],
                splitFields[USUARIO_UUID],
                splitFields[RESTAURANTE_UUID],
                splitMenus)) throw new DeserializerException();
        try {
            restaurante = queryService.getRestauranteByUuid(splitFields[RESTAURANTE_UUID]);
            usuario = queryService.getUsuarioByUuid(splitFields[USUARIO_UUID]);
            Menu menu;
            for (String menuUUID : splitMenus) {
                menu = queryService.getMenuByUuid(menuUUID);
                if (restaurante.getUuid() != menu.getRestaurante().getUuid()) {
                    throw new DeserializerException();
                }
                menus.add(menu);
            }
        } catch (Exception e) {
            throw new DeserializerException();
        }
        this.pedido = new Pedido.Builder()
                .withCalle(splitFields[CALLE])
                .withNumero(splitFields[NUMERO])
                .withPiso(splitFields[PISO])
                .withDepartamento(splitFields[DEPARTARMENTO])
                .withTelefono(splitFields[TELEFONO])
                .withFecha(LocalDateTime.now())
                .withRestaurante(restaurante)
                .withUsuario(usuario)
                .withMenus(menus)
                .build();
    }

    public Pedido getPedido() {
        return pedido;
    }
}
