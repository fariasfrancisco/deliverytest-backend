package com.safira.service.deserialize;

import com.safira.common.DeserializerException;
import com.safira.entities.Menu;
import com.safira.entities.Restaurante;
import com.safira.service.hibernate.QueryService;

import java.math.BigDecimal;

/**
 * Created by Francisco on 10/03/2015.
 */
public class MenuDeserializer {
    private static final String FIELD_SEPARATOR = ":";
    private static final int NOMBRE = 0;
    private static final int DESCRIPCION = 1;
    private static final int COSTO = 2;
    private static final int RESTAURANTE_ID = 3;

    private Menu menu;

    public MenuDeserializer(String serializedMenu) throws DeserializerException {
        Restaurante restaurante;
        BigDecimal costo;
        QueryService queryService = new QueryService();
        String[] splitFields = serializedMenu.split(FIELD_SEPARATOR);
        if (splitFields.length != 4) {
            throw new DeserializerException();
        }
        try {
            costo = new BigDecimal(splitFields[COSTO]);
            restaurante = queryService.getRestauranteById(Integer.valueOf(splitFields[RESTAURANTE_ID]));
        } catch (Exception e) {
            throw new DeserializerException();
        }
        this.menu = new Menu.Builder()
                .withNombre(splitFields[NOMBRE])
                .withDescripcion(splitFields[DESCRIPCION])
                .withCosto(costo)
                .withRestaurante(restaurante)
                .build();
    }

    public Menu getMenu() {
        return menu;
    }
}