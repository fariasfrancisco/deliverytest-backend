package com.safira.service.deserialize;

import com.safira.common.DeserializerException;
import com.safira.entities.Menu;
import com.safira.entities.Pedido;
import com.safira.entities.Restaurante;
import com.safira.entities.Usuario;
import com.safira.service.hibernate.HibernateSessionService;
import com.safira.service.hibernate.QueryService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Francisco on 08/03/2015.
 */
public class PedidoDeserializer {

    /**
     * Desired jSon format:
     * {"serializedObject":"CALLE:NUMERO:PISO:DEPARTARMENTO:TELEFONO:FACEBOOK_ID:RESTAURANTE_ID:MENU_ID&...&MENU_ID"}
     */

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

    private Pedido pedido;

    public PedidoDeserializer(String serializedPedido) throws DeserializerException {
        Restaurante restaurante;
        Usuario usuario;
        Set<Menu> menus = new HashSet<>();
        Set<Menu> recievedMenus = new HashSet<>();
        String[] splitFields = serializedPedido.split(FIELD_SEPARATOR);
        if (splitFields.length < 8) {
            throw new DeserializerException();
        }
        String[] splitMenus = splitFields[MENUS].split(MENU_SEPARATOR);
        try {                                                                                                           //This code is here until I learn
            QueryService queryService = new QueryService();                                                             //how to solve the null foreign
            restaurante = queryService.getRestauranteById(Integer.valueOf(splitFields[RESTAURANTE_ID]));                //object recieved with select
            menus = restaurante.getMenus();                                                                             //query.
            /*Menus menuses = new Menus(queryService.getMenusByRestauranteId(Integer.valueOf(splitFields[RESTAURANTE_ID]));
            List<Menu> menusList = menuses.getMenus();*/
            usuario = queryService.getUsuario(splitFields[FACEBOOK_ID]);
            Menu menu;
            boolean found;
            for (String menuId : splitMenus) {
                found = false;
                menu = queryService.getMenuById(Integer.valueOf(menuId));
                for (Menu m : menus) {
                    if (m.getId() == menu.getId()) {
                        found = true;
                    }
                }
                if (!found) {
                    throw new DeserializerException();
                }
                menu.setRestaurante(restaurante);
                recievedMenus.add(menu);
            }
        } catch (Exception e) {
            throw new DeserializerException();
        } finally {
            HibernateSessionService.shutDown();
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