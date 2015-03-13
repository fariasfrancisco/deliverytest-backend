package com.safira.service.deserialize;

import com.safira.common.Regex;
import com.safira.common.exceptions.DeserializerException;
import com.safira.entities.Menu;
import com.safira.entities.Restaurante;
import com.safira.service.hibernate.QueryService;

import java.math.BigDecimal;

/**
 * Class in charge of deserilizing recieved String and create a Menu object out of it.
 */
public class MenuDeserializer {

    /**
     * Desired jSon format:
     * {"serializedObject":"NOMBRE;DESCRIPCION;COSTO;RESTAURANTE_ID"}
     */

    private static final String FIELD_SEPARATOR = ";";
    private static final int NOMBRE = 0;
    private static final int DESCRIPCION = 1;
    private static final int COSTO = 2;
    private static final int RESTAURANTE_ID = 3;

    private Menu menu;

    public MenuDeserializer(String serializedMenu, QueryService queryService) throws DeserializerException {
        Restaurante restaurante;
        BigDecimal costo;
        String[] splitFields = serializedMenu.split(FIELD_SEPARATOR);
        if (splitFields.length != 4) {
            throw new DeserializerException();
        }
        validate(splitFields);
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

    private void validate(String[] splitFields) throws DeserializerException {
        if (!splitFields[COSTO].matches(Regex.MONEY_FORMAT)) throw new DeserializerException();
        if (!splitFields[RESTAURANTE_ID].matches(Regex.ID_FORMAT)) throw new DeserializerException();
    }
}