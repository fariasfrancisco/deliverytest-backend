package com.safira.service;

import com.safira.api.CreateMenuRequest;
import com.safira.service.implementation.MenuServiceImpl;
import com.safira.service.interfaces.MenuService;
import org.junit.Before;

import java.math.BigDecimal;

/**
 * Created by francisco on 28/03/15.
 */
public class MenuServiceTest {

    private CreateMenuRequest validRequest;
    private MenuService menuService;

    @Before
    public void setUp() {
        menuService = new MenuServiceImpl();
        validRequest =
                new CreateMenuRequest()
                        .setRestauranteUuid("1234qwer-1234-qwer-asdf-1234qwerasdf")
                        .setCosto(new BigDecimal(12.12))
                        .setNombre("testMenuName")
                        .setDescripcion("testDescription");
    }


}
