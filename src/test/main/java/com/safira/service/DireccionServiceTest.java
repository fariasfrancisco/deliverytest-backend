package com.safira.service;

import com.safira.api.CreateDireccionRequest;
import com.safira.common.exceptions.ValidatorException;
import com.safira.service.implementation.DireccionServiceImpl;
import com.safira.service.interfaces.DireccionService;
import com.safira.service.repositories.DireccionRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doNothing;

/**
 * Created by francisco on 27/03/15.
 */
public class DireccionServiceTest {

    private DireccionService direccionService;
    private CreateDireccionRequest requestWithAlphanumericInNumero;
    private CreateDireccionRequest requestWithNullPisoAndNotNullDepartamento;
    private CreateDireccionRequest validRequest;
    private DireccionRepository direccionRepository;

    @Before
    public void setUp() {
        direccionService = new DireccionServiceImpl();

        requestWithAlphanumericInNumero =
                new CreateDireccionRequest()
                        .setCalle("calletest")
                        .setNumero("testnumber")
                        .setPiso(null)
                        .setDepartamento(null);
        requestWithNullPisoAndNotNullDepartamento =
                new CreateDireccionRequest()
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso(null)
                        .setDepartamento("A");
        validRequest =
                new CreateDireccionRequest()
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso("2")
                        .setDepartamento("A");

    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidationExceptionWhenNumeroIsNotNumeric() throws ValidatorException {
        direccionService.createDireccion(requestWithAlphanumericInNumero);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidationExceptionWhenPisoIsNullButDepartamentoIsNot() throws ValidatorException {
        direccionService.createDireccion(requestWithNullPisoAndNotNullDepartamento);
    }

    @Test
    public void shouldCreateDireccionObject() {

    }
}
