package com.safira.service;

import com.safira.api.*;
import com.safira.common.exceptions.*;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by francisco on 21/03/15.
 */
public class Validator {

    public static final String UUID_FORMAT = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
    public static final String PHONE_FORMAT = "^\\d{1,16}$";
    public static final String USERNAME_FORMAT = "^([0-9]*[a-z]*[A-Z]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String PASSWORD_FORMAT = "^([0-9]*[a-z]*[A-Z]*[@#$%^&+=]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String NUMBER_FORMAT = "^\\d+$";

    public static void validateUsuario(CreateUsuarioRequest createUsuarioRequest) throws ValidatorException {
        if (!validateEmail(createUsuarioRequest.getEmail()))
            throw new EmailException("The format in the field email("
                    + createUsuarioRequest.getEmail() + ") is invalid",
                    "Please check the information submited and try again.");
    }

    public static void validateMenu(CreateMenuRequest createMenuRequest) throws ValidatorException {
        if (!validateUuid(createMenuRequest.getRestauranteUuid()))
            throw new UUIDException("The format in the field restauranteUuid(" +
                    createMenuRequest.getRestauranteUuid() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validateMoney(createMenuRequest.getCosto()))
            throw new MoneyException("The format in the field restauranteUUid(" +
                    createMenuRequest.getCosto() + ") is invalid",
                    "Please check the information submited and try again.");
    }

    public static void validateRestaurante(CreateRestauranteRequest createRestauranteRequest) throws ValidatorException {
        if (!validateUsername(createRestauranteRequest.getUsuario()))
            throw new UsernameException("The format in the field usuario(" +
                    createRestauranteRequest.getUsuario() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validateNumber(createRestauranteRequest.getNumero()))
            throw new NumberException("The format in the field numero(" +
                    createRestauranteRequest.getNumero() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validatePhone(createRestauranteRequest.getTelefono()))
            throw new PhoneException("The format in the field telefono(" +
                    createRestauranteRequest.getTelefono() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validateEmail(createRestauranteRequest.getEmail()))
            throw new EmailException("The format in the field email(" +
                    createRestauranteRequest.getEmail() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validatePassword(createRestauranteRequest.getPassword()))
            throw new PasswordException("The format in the field password(" +
                    createRestauranteRequest.getPassword() + ") is invalid",
                    "Please check the information submited and try again.");
    }

    public static void validatePedido(CreatePedidoRequest createPedidoRequest) throws ValidatorException, InconsistencyException {
        if (!validateCantidades(createPedidoRequest.getCantidades()))
            throw new AmmountException("The format in at least one of the values in the field cantidades is invalid",
                    "Please check the information submited and try again.");
        if (createPedidoRequest.getMenuUuids().length != createPedidoRequest.getCantidades().length)
            throw new InconsistencyException("The ammount of menus (" + createPedidoRequest.getMenuUuids().length
                    + ") does not match the ammount of cantidad(" + createPedidoRequest.getCantidades().length + ")",
                    "Please check the information submited and try again.");
        if (!validateUuid(createPedidoRequest.getDireccionUuid()))
            throw new UUIDException("The format in the field direccionUuid(" +
                    createPedidoRequest.getDireccionUuid() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validatePhone(createPedidoRequest.getTelefono()))
            throw new PhoneException("The format in the field telefono(" +
                    createPedidoRequest.getTelefono() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validateUuid(createPedidoRequest.getUsuarioUuid()))
            throw new UUIDException("The format in the field usuarioUuid(" +
                    createPedidoRequest.getUsuarioUuid() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validateUuid(createPedidoRequest.getRestauranteUuid()))
            throw new UUIDException("The format in the field restauranteUuid(" +
                    createPedidoRequest.getRestauranteUuid() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validateMenuArray(createPedidoRequest.getMenuUuids()))
            throw new UUIDException("The format in the field menuUuids is invalid",
                    "Please check the information submited and try again.");
        if (!validateDate(createPedidoRequest.getFecha()))
            throw new DateException("The recieved date is invalid: " + createPedidoRequest.getFecha().toString(),
                    "Please check the information submited and try again.");
    }

    public static void validateRestauranteLogin(LoginRestauranteRequest loginRestauranteRequest) throws ValidatorException {
        if (!validateUsername(loginRestauranteRequest.getUsuario()))
            throw new UsernameException("The format in the field usuario(" +
                    loginRestauranteRequest.getUsuario() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validatePassword(loginRestauranteRequest.getPassword()))
            throw new PasswordException("The format in the field password(" +
                    loginRestauranteRequest.getPassword() + ") is invalid",
                    "Please check the information submited and try again.");
    }

    public static void validateDireccion(CreateDireccionRequest createDireccionRequest) throws ValidatorException {
        if (!validateUuid(createDireccionRequest.getUsuarioUuid()))
            throw new UUIDException("The format in the field usuarioUuid(" +
                    createDireccionRequest.getUsuarioUuid() + ") is invalid",
                    "Please check the information submited and try again.");
        if (!validateNumber(createDireccionRequest.getNumero()))
            throw new NumberException("The format in the field numero(" +
                    createDireccionRequest.getNumero() + ") is invalid",
                    "Please check the information submited and try again.");
        if (createDireccionRequest.getPiso() == null)
            if (createDireccionRequest.getDepartamento() != null)
                throw new DireccionException("If piso is null departamento must be null.",
                        "Please check the information submited and try again.");
    }

    private static boolean validateMenuArray(String[] menuUuids) {
        for (String uuid : menuUuids) if (!validateUuid(uuid)) return false;
        return true;
    }

    private static boolean validateCantidades(BigDecimal[] cantidades) {
        for (BigDecimal cantidad : cantidades)
            if (cantidad.signum() != 1) return false;
        return true;
    }

    private static boolean validateMoney(BigDecimal money) {
        return (money.scale() < 3) && (money.signum() == 1);
    }

    private static boolean validateUuid(String uuid) {
        return uuid.matches(UUID_FORMAT);
    }

    private static boolean validatePhone(String phone) {
        return phone.matches(PHONE_FORMAT);
    }

    private static boolean validateUsername(String username) {
        return username.matches(USERNAME_FORMAT);
    }

    private static boolean validatePassword(String password) {
        return password.matches(PASSWORD_FORMAT);
    }

    private static boolean validateNumber(String number) {
        return number.matches(NUMBER_FORMAT);
    }

    private static boolean validateEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private static boolean validateDate(LocalDateTime date) {
        return (date.isBefore(LocalDateTime.now()));
    }
}
