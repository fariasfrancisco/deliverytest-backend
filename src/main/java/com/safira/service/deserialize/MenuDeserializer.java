package com.safira.service.deserialize;

import com.safira.common.exceptions.DeserializerException;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Class in charge of deserilizing recieved String and create a Menu object out of it.
 */
public class MenuDeserializer {

    /**
     * Desired jSon format:
     * {"serializedObject":"NOMBRE;DESCRIPCION;COSTO;RESTAURANTE_UUID"}
     */

    private static final String FIELD_SEPARATOR = ";";
    private static final int NOMBRE = 0;
    private static final int DESCRIPCION = 1;
    private static final int COSTO = 2;
    private static final int RESTAURANTE_UUID = 3;

    @Autowired
    RestauranteRepository repository;

    private Menu menu;

    public MenuDeserializer(String serializedMenu) throws DeserializerException {
        Restaurante restaurante;
        BigDecimal costo;
        String[] splitFields = serializedMenu.split(FIELD_SEPARATOR);
        if (splitFields.length != 4) {
            throw new DeserializerException();
        }
        Validator.validateMenu(splitFields[COSTO], splitFields[RESTAURANTE_UUID]);
        try {
            costo = new BigDecimal(splitFields[COSTO]);
            restaurante = repository.findByUuid(splitFields[RESTAURANTE_UUID]);
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
