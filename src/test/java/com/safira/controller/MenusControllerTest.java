package com.safira.controller;

import com.safira.domain.SerializedObject;
import com.safira.domain.entities.Menu;
import com.safira.domain.entities.Restaurante;
import com.safira.service.hibernate.QueryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by Francisco on 10/03/2015.
 */
public class MenusControllerTest {

    QueryService queryServiceMock;
    MenusController menusController;
    SerializedObject serializedObjectStub;
    Menu menuStub;
    Restaurante restauranteMock;
    ResponseEntity<Object> responseEntityStub;

    @Before
    public void setUp() {
        queryServiceMock = mock(QueryService.class);
        restauranteMock = mock(Restaurante.class);
        serializedObjectStub = new SerializedObject("StubName;StubDescription;12.34;0");
        responseEntityStub = new ResponseEntity<>(menuStub, HttpStatus.OK);
        menuStub = new Menu.Builder()
                .withNombre("StubName")
                .withDescripcion("StubDescription")
                .withCosto(new BigDecimal(12.34))
                .withRestaurante(restauranteMock)
                .build();
        menusController = new MenusController();
    }

    @Test
    public void shouldGenerateMenuObject() {
        /*when(queryServiceMock.getRestauranteByUuid(0)).thenReturn(restauranteMock);
        ResponseEntity<Object> result = menusController.registerMenu(serializedObjectStub);
        assertEquals(responseEntityStub,result);*/
    }

    @Test
    public void shouldGenerateMenuXML() {

    }
}
