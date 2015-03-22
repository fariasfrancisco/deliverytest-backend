package com.safira.service.deserialize;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.service.Validator;

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

    private Menu menu;

    public MenuDeserializer(String serializedMenu, RestauranteRepository restauranteRepository)
            throws DeserializerException, ValidatorException, JPAQueryException {
        Restaurante restaurante;
        String[] splitFields = serializedMenu.split(FIELD_SEPARATOR);
        if (splitFields.length != 4)
            throw new DeserializerException("Desearilization Failed. " +
                    "The serializedObject recieved does not meet the length requirements.");
        BigDecimal costo = new BigDecimal(splitFields[COSTO]);
        Validator.validateMenu(splitFields[COSTO], splitFields[RESTAURANTE_UUID]);
        restaurante = restauranteRepository.findByUuid(splitFields[RESTAURANTE_UUID]);
        System.out.println(restaurante);
        if (restaurante == null) throw new JPAQueryException("Desearilization Failed. " +
                "No restaurante found with uuid = " + splitFields[RESTAURANTE_UUID]);
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
