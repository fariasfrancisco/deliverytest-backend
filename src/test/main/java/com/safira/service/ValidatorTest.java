package com.safira.service;

import com.safira.api.*;
import com.safira.common.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by francisco on 29/03/15.
 */
public class ValidatorTest {

    private CreateUsuarioRequest usuarioRequestWithInvalidEmailAddress;
    private CreateRestauranteRequest restauranteRequestWithInvalidUsuario;
    private CreateRestauranteRequest restauranteRequestWithInvalidNumero;
    private CreateRestauranteRequest restauranteRequestWithInvalidTelefono;
    private CreateRestauranteRequest restauranteRequestWithInvalidEmail;
    private CreateRestauranteRequest restauranteRequestWithInvalidPassword;
    private CreatePedidoRequest pedidoRequestWithInvalidDireccionUuid;
    private CreatePedidoRequest pedidoRequestWithInvalidPhone;
    private CreatePedidoRequest pedidoRequestWithInvalidUsuarioUuid;
    private CreatePedidoRequest pedidoRequestWithInvalidRestauranteUuid;
    private CreatePedidoRequest pedidoRequestWithInvalidMenuUuids;
    private CreatePedidoRequest pedidoRequestWithInvalidDate;
    private CreatePedidoRequest pedidoRequestWithDifferentAmmountOfCantidadAndMenus;
    private CreateDireccionRequest direccionRequestWithAlphanumericInNumero;
    private CreateDireccionRequest direccionRequestWithNullPisoAndNotNullDepartamento;
    private CreateDireccionRequest direccionRequestWithInvalidUsuarioUuid;
    private CreateMenuRequest menuRequestWithThreeDecimalPlacesInCosto;
    private CreateMenuRequest menuRequestWithInvalidRestauranteUuid;

    @Before
    public void setUp() throws Exception {
        String[] validMenuUuids = new String[1];
        String[] invalidMenuUuids = new String[1];
        BigDecimal[] validCantidad = new BigDecimal[1];
        BigDecimal[] invalidCantidad = new BigDecimal[2];
        validMenuUuids[0] = "6eab2b70-d665-11e4-8830-0800200c9a66";
        invalidMenuUuids[0] = "6eab2x70-d665-11e4-8830-0800200c9a66";
        validCantidad[0] = new BigDecimal(1);
        invalidCantidad[0] = new BigDecimal(1);
        usuarioRequestWithInvalidEmailAddress =
                new CreateUsuarioRequest()
                        .setFacebookId("123123123123")
                        .setApellido("TestLastName")
                        .setNombre("TestFirstName")
                        .setEmail("TstInvalidMail123");
        restauranteRequestWithInvalidUsuario =
                new CreateRestauranteRequest()
                        .setNombre("TestName")
                        .setCalle("TestStreet")
                        .setNumero("123")
                        .setTelefono("123789456")
                        .setEmail("test@domain.com")
                        .setUsuario("test")
                        .setPassword("testpassword");
        restauranteRequestWithInvalidNumero =
                new CreateRestauranteRequest()
                        .setNombre("TestName")
                        .setCalle("TestStreet")
                        .setNumero("testnumber")
                        .setTelefono("123789456")
                        .setEmail("test@domain.com")
                        .setUsuario("testuser")
                        .setPassword("testpassword");
        restauranteRequestWithInvalidTelefono =
                new CreateRestauranteRequest()
                        .setNombre("TestName")
                        .setCalle("TestStreet")
                        .setNumero("123")
                        .setTelefono("123qweasd")
                        .setEmail("test@domain.com")
                        .setUsuario("testuser")
                        .setPassword("testpassword");
        restauranteRequestWithInvalidEmail =
                new CreateRestauranteRequest()
                        .setNombre("TestName")
                        .setCalle("TestStreet")
                        .setNumero("123")
                        .setTelefono("1235674")
                        .setEmail("testdomaincom")
                        .setUsuario("testuser")
                        .setPassword("testpassword");
        restauranteRequestWithInvalidPassword =
                new CreateRestauranteRequest()
                        .setNombre("TestName")
                        .setCalle("TestStreet")
                        .setNumero("123")
                        .setTelefono("123890567")
                        .setEmail("test@domain.com")
                        .setUsuario("testuser")
                        .setPassword("12");
        pedidoRequestWithInvalidDireccionUuid =
                new CreatePedidoRequest()
                        .setDireccionUuid("6eap2b70-d665-11e4-8830-0800200c9a66")
                        .setRestauranteUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setFecha(LocalDateTime.now())
                        .setMenuUuids(validMenuUuids)
                        .setCantidades(validCantidad)
                        .setTelefono("12345678");
        pedidoRequestWithInvalidPhone =
                new CreatePedidoRequest()
                        .setDireccionUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setRestauranteUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setFecha(LocalDateTime.now())
                        .setMenuUuids(validMenuUuids)
                        .setCantidades(validCantidad)
                        .setTelefono("123asd45678");
        pedidoRequestWithInvalidUsuarioUuid =
                new CreatePedidoRequest()
                        .setDireccionUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setRestauranteUuid("6ebb2b70-d665-11e4-8830-0800200c9a66")
                        .setUsuarioUuid("6eab2bx0-d665-11e4-8830-0800200c9a66")
                        .setFecha(LocalDateTime.now())
                        .setMenuUuids(validMenuUuids)
                        .setCantidades(validCantidad)
                        .setTelefono("12345678");
        pedidoRequestWithInvalidRestauranteUuid =
                new CreatePedidoRequest()
                        .setDireccionUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setRestauranteUuid("6eyb2b70-d665-11e4-8830-0800200c9a66")
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setFecha(LocalDateTime.now())
                        .setMenuUuids(validMenuUuids)
                        .setCantidades(validCantidad)
                        .setTelefono("12345678");
        pedidoRequestWithInvalidMenuUuids =
                new CreatePedidoRequest()
                        .setDireccionUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setRestauranteUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setFecha(LocalDateTime.now())
                        .setMenuUuids(invalidMenuUuids)
                        .setCantidades(validCantidad)
                        .setTelefono("12345678");
        pedidoRequestWithInvalidDate =
                new CreatePedidoRequest()
                        .setDireccionUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setRestauranteUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setFecha(LocalDateTime.now().plusDays(12))
                        .setMenuUuids(validMenuUuids)
                        .setCantidades(validCantidad)
                        .setTelefono("12345678");
        pedidoRequestWithDifferentAmmountOfCantidadAndMenus =
                new CreatePedidoRequest()
                        .setDireccionUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setRestauranteUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setFecha(LocalDateTime.now())
                        .setMenuUuids(validMenuUuids)
                        .setCantidades(invalidCantidad)
                        .setTelefono("12345678");
        direccionRequestWithAlphanumericInNumero =
                new CreateDireccionRequest()
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setCalle("calletest")
                        .setNumero("testnumber")
                        .setPiso(null)
                        .setDepartamento(null);
        direccionRequestWithNullPisoAndNotNullDepartamento =
                new CreateDireccionRequest()
                        .setUsuarioUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso(null)
                        .setDepartamento("A");
        direccionRequestWithInvalidUsuarioUuid =
                new CreateDireccionRequest()
                        .setUsuarioUuid("1234asdf1234asdfqwer1234adsfqwer")
                        .setCalle("calletest")
                        .setNumero("1234")
                        .setPiso(null)
                        .setDepartamento("null");
        menuRequestWithInvalidRestauranteUuid =
                new CreateMenuRequest()
                        .setRestauranteUuid("1234qwer1234qwerasdf1234qwerasdf")
                        .setCosto(new BigDecimal(12.12))
                        .setNombre("testMenuName")
                        .setDescripcion("testDescription");
        menuRequestWithThreeDecimalPlacesInCosto =
                new CreateMenuRequest()
                        .setRestauranteUuid("6eab2b70-d665-11e4-8830-0800200c9a66")
                        .setCosto(new BigDecimal("12.123"))
                        .setNombre("testMenuName")
                        .setDescripcion("testDescription");
    }

    @Test(expected = EmailException.class)
    public void shouldThrowEmailExceptionWhenRecievingAnInvalidUsuarioEmailAddress() throws Exception {
        Validator.validateUsuario(usuarioRequestWithInvalidEmailAddress);
    }

    @Test(expected = UUIDException.class)
    public void shouldThrowUUIDExceptionWhenRecievingAMenuWithInvalidRestauranteUuid() throws ValidatorException {
        Validator.validateMenu(menuRequestWithInvalidRestauranteUuid);
    }

    @Test(expected = MoneyException.class)
    public void shouldThrowMoneyExceptionWhenRecievingAMenuCostoWithMoreThanTwoDecimalPlaces() throws ValidatorException {
        Validator.validateMenu(menuRequestWithThreeDecimalPlacesInCosto);
    }

    @Test(expected = UsernameException.class)
    public void shouldThrowUsernameExceptionWhenRecievingARestauranteWithInvalidUsuario() throws ValidatorException {
        Validator.validateRestaurante(restauranteRequestWithInvalidUsuario);
    }

    @Test(expected = PasswordException.class)
    public void shouldThrowPasswordExceptionWhenRecievingARestauranteWithInvalidPassword() throws ValidatorException {
        Validator.validateRestaurante(restauranteRequestWithInvalidPassword);
    }

    @Test(expected = NumberException.class)
    public void shouldThrowNumberExceptionWhenRecievingARestauranteWithInvalidNumero() throws ValidatorException {
        Validator.validateRestaurante(restauranteRequestWithInvalidNumero);
    }

    @Test(expected = PhoneException.class)
    public void shouldThrowPhoneExceptionWhenRecievingARestauranteWithInvalidTelefono() throws ValidatorException {
        Validator.validateRestaurante(restauranteRequestWithInvalidTelefono);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidatorExceptionWhenRecievingARestauranteWithInvalidEmail() throws ValidatorException {
        Validator.validateRestaurante(restauranteRequestWithInvalidEmail);
    }

    @Test(expected = DateException.class)
    public void shouldThrowDateExceptionWhenRecievingAPedidoWithADateAfterNow() throws ValidatorException, InconsistencyException {
        Validator.validatePedido(pedidoRequestWithInvalidDate);
    }

    @Test(expected = UUIDException.class)
    public void shouldThrowDateExceptionWhenRecievingAPedidoWithAnInvalidDireccionUuid() throws ValidatorException, InconsistencyException {
        Validator.validatePedido(pedidoRequestWithInvalidDireccionUuid);
    }

    @Test(expected = UUIDException.class)
    public void shouldThrowDateExceptionWhenRecievingAPedidoWithAnInvalidRestauranteUuid() throws ValidatorException, InconsistencyException {
        Validator.validatePedido(pedidoRequestWithInvalidRestauranteUuid);
    }

    @Test(expected = UUIDException.class)
    public void shouldThrowDateExceptionWhenRecievingAPedidoWithAnInvalidUsuarioUuid() throws ValidatorException, InconsistencyException {
        Validator.validatePedido(pedidoRequestWithInvalidUsuarioUuid);
    }

    @Test(expected = PhoneException.class)
    public void shouldThrowDateExceptionWhenRecievingAPedidoWithAnInvalidTelefono() throws ValidatorException, InconsistencyException {
        Validator.validatePedido(pedidoRequestWithInvalidPhone);
    }

    @Test(expected = UUIDException.class)
    public void shouldThrowDateExceptionWhenRecievingAPedidoWithAnInvalidArrayOfMenuUuids() throws ValidatorException, InconsistencyException {
        Validator.validatePedido(pedidoRequestWithInvalidMenuUuids);
    }

    @Test(expected = InconsistencyException.class)
    public void shouldThrowInconsistencyExceptionWhenRecievingAPedidoWithDifferentAmmountsOfCantidadAndMenus() throws ValidatorException, InconsistencyException {
        Validator.validatePedido(pedidoRequestWithDifferentAmmountOfCantidadAndMenus);
    }

    @Test
    public void testValidateRestauranteLogin() throws Exception {

    }

    @Test(expected = NumberException.class)
    public void shouldThrowNumberExceptionWhenNumeroIsNotNumeric() throws ValidatorException {
        Validator.validateDireccion(direccionRequestWithAlphanumericInNumero);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowValidationExceptionWhenPisoIsNullButDepartamentoIsNot() throws ValidatorException {
        Validator.validateDireccion(direccionRequestWithNullPisoAndNotNullDepartamento);
    }

    @Test(expected = UUIDException.class)
    public void shouldThrowUUIDExceptionWhenUsuarioUuidIsInvalid() throws ValidatorException {
        Validator.validateDireccion(direccionRequestWithInvalidUsuarioUuid);
    }
}
