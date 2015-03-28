package com.safira.service;

import com.safira.api.CreateDireccionRequest;
import com.safira.common.exceptions.JPAQueryException;
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
    private CreateDireccionRequest requestWithInvalidUsuarioUuid;
    private CreateDireccionRequest validRequest;

    @Before
    public void setUp() {
        direccionService = new DireccionServiceImpl();
        requestWithAlphanumericInNumero =
                new CreateDireccionRequest()
                        .setUsuarioUuid("1234asdf-1234-asdf-qwer-1234adsfqwer")
                        .setCalle("calletest")
                        .setNumero("testnumber")
                        .setPiso(null)
                        .setDepartamento(null);
        requestWithNullPisoAndNotNullDepartamento =
                new CreateDireccionRequest()
                        .setUsuarioUuid("1234asdf-1234-asdf-qwer-1234adsfqwer")
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso(null)
                        .setDepartamento("A");
        requestWithInvalidUsuarioUuid =
                new CreateDireccionRequest()
                        .setUsuarioUuid("1234asdf1234asdfqwer1234adsfqwer")
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso(null)
                        .setDepartamento("null");
        validRequest =
                new CreateDireccionRequest()
                        .setUsuarioUuid("1234asdf-1234-asdf-qwer-1234adsfqwer")
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso("2")
                        .setDepartamento("A");

    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidationExceptionWhenNumeroIsNotNumeric() throws ValidatorException, JPAQueryException {
        direccionService.createDireccion(requestWithAlphanumericInNumero);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidationExceptionWhenPisoIsNullButDepartamentoIsNot() throws ValidatorException, JPAQueryException {
        direccionService.createDireccion(requestWithNullPisoAndNotNullDepartamento);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidatorExceptionWhenUsuarioUuidIsInvalid() throws ValidatorException, JPAQueryException {
        direccionService.createDireccion(requestWithInvalidUsuarioUuid);
    }

    @Test
    public void shouldCreateDireccionObject() {

    }
}
