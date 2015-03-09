package com.safira.service;

import com.safira.entities.Menu;
import com.safira.entities.Pedido;
import com.safira.entities.Restaurante;
import com.safira.entities.Usuario;
import com.safira.service.Hibernate.HibernateSessionService;
import com.safira.service.Hibernate.QueryService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Francisco on 08/03/2015.
 */
public class PedidoDeserializer {

    private static final String FIELD_SEPARATOR = ":";
    private static final String MENU_SEPARATOR = "&";
    private static final int CALLE = 0;
    private static final int NUMERO = 1;
    private static final int PISO = 2;
    private static final int DEPARTARMENTO = 3;
    private static final int TELEFONO = 4;
    private static final int FACEBOOK_ID = 5;
    private static final int RESTAURANTE_ID = 6;
    private static final int MENUS = 7;

    private String[] splitFields;
    private String[] splitMenus;
    private Restaurante restaurante;
    private Usuario usuario;
    private Set<Menu> menus = new HashSet<>();
    private Pedido pedido;

    public PedidoDeserializer(String serializedPedido) {
        splitFields = serializedPedido.split(FIELD_SEPARATOR);
        splitMenus = splitFields[MENUS].split(MENU_SEPARATOR);
        QueryService queryService = new QueryService();
        restaurante = queryService.getRestauranteById(Integer.valueOf(splitFields[RESTAURANTE_ID]));
        usuario = queryService.getUsuario(splitFields[FACEBOOK_ID]);
        Menu menu;
        for (String menuId : splitMenus) {
            menu = queryService.getMenuById(Integer.valueOf(menuId));
            menus.add(menu);
        }
        pedido = new Pedido.Builder()
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
        HibernateSessionService.shutDown();
    }

    public Pedido getPedido() {
        return pedido;
    }
}
