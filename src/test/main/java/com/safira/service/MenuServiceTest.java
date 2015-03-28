package com.safira.service;

import com.safira.api.CreateMenuRequest;
import com.safira.common.exceptions.JPAQueryException;
import com.safira.common.exceptions.ValidatorException;
import com.safira.service.implementation.MenuServiceImpl;
import com.safira.service.interfaces.MenuService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by francisco on 28/03/15.
 */
public class MenuServiceTest {
    private CreateMenuRequest requestWithThreeDecimalPlacesInCosto;
    private CreateMenuRequest requestWithInvalidRestauranteUuid;
    private CreateMenuRequest validRequest;
    private MenuService menuService;

    @Before
    public void setUp() {
        menuService = new MenuServiceImpl();
        requestWithInvalidRestauranteUuid =
                new CreateMenuRequest()
                        .setRestauranteUuid("1234qwer1234qwerasdf1234qwerasdf")
                        .setCosto(new BigDecimal(12.12))
                        .setNombre("testMenuName")
                        .setDescripcion("testDescription");

        requestWithThreeDecimalPlacesInCosto =
                new CreateMenuRequest()
                        .setRestauranteUuid("1234qwer-1234-qwer-asdf-1234qwerasdf")
                        .setCosto(new BigDecimal(12.123))
                        .setNombre("testMenuName")
                        .setDescripcion("testDescription");
        validRequest =
                new CreateMenuRequest()
                        .setRestauranteUuid("1234qwer-1234-qwer-asdf-1234qwerasdf")
                        .setCosto(new BigDecimal(12.12))
                        .setNombre("testMenuName")
                        .setDescripcion("testDescription");
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidatorExceptionWhenRecievingAnInvalidUuid() throws ValidatorException, JPAQueryException {
        menuService.createMenu(requestWithInvalidRestauranteUuid);
    }
}
