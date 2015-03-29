package com.safira.service;

import com.safira.api.CreateDireccionRequest;
import com.safira.service.implementation.DireccionServiceImpl;
import com.safira.service.interfaces.DireccionService;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by francisco on 27/03/15.
 */
public class DireccionServiceTest {

    private DireccionService direccionService;

    private CreateDireccionRequest validRequest;

    @Before
    public void setUp() {
        direccionService = new DireccionServiceImpl();
        validRequest =
                new CreateDireccionRequest()
                        .setUsuarioUuid("1234asdf-1234-asdf-qwer-1234adsfqwer")
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso("2")
                        .setDepartamento("A");
    }


    @Test
    public void shouldCreateDireccionObject() {

    }
}
