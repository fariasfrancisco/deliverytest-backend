package com.safira.service.deserialize;

import com.safira.common.exceptions.DeserializerException;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Pedido;
import com.safira.domain.entities.Restaurante;
import com.safira.domain.entities.Usuario;
import com.safira.domain.repositories.MenuRepository;
import com.safira.domain.repositories.RestauranteRepository;
import com.safira.domain.repositories.UsuarioRepository;
import com.safira.service.Validator;

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

    public PedidoDeserializer(String serializedPedido,
                              RestauranteRepository restauranteRepository,
                              UsuarioRepository usuarioRepository,
                              MenuRepository menuRepository)
            throws DeserializerException, ValidatorException, JPAQueryException, InconsistencyException {
        Restaurante restaurante;
        Usuario usuario;
        Set<Menu> menus = new HashSet<>();
        String[] splitFields = serializedPedido.split(FIELD_SEPARATOR);
        if (splitFields.length < 8)
            throw new DeserializerException("The serializedObject recieved does not meet the length requirements.");
        String[] splitMenus = splitFields[MENUS].split(MENU_SEPARATOR);
        Validator.validatePedido(splitFields[NUMERO],
                splitFields[TELEFONO],
                splitFields[USUARIO_UUID],
                splitFields[RESTAURANTE_UUID],
                splitMenus);
        restaurante = restauranteRepository.findByUuid(splitFields[RESTAURANTE_UUID]);
        if (restaurante == null) throw new JPAQueryException("Desearilization Failed. " +
                "No restaurante found with uuid = " + splitFields[RESTAURANTE_UUID]);
        usuario = usuarioRepository.findByUuid(splitFields[USUARIO_UUID]);
        if (usuario == null) throw new JPAQueryException("Desearilization Failed. " +
                "No usuario found with uuid = " + splitFields[USUARIO_UUID]);
        Menu menu;
        for (String menuUuid : splitMenus) {
            menu = menuRepository.findByUuid(menuUuid);
            if (menu == null) throw new JPAQueryException("Desearilization Failed. " +
                    "No menu found with uuid = " + menuUuid);
            if (!restaurante.getIdentifier().equals(menu.getRestaurante().getIdentifier())) {
                System.out.println(restaurante.getIdentifier());
                System.out.println(menu.getRestaurante().getIdentifier());
                throw new InconsistencyException("Deserialization Failed. " +
                        "menu.restaurante uuid recieved was " + menuUuid +
                        ". Expected " + splitFields[RESTAURANTE_UUID]);
            }
            menus.add(menu);
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
