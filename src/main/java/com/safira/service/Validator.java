package com.safira.service;

import com.safira.api.requests.*;
import com.safira.api.responses.AuthenticatedRestauranteToken;
import com.safira.common.ErrorOutput;
import com.safira.common.exceptions.InconsistencyException;
import com.safira.common.exceptions.ValidatorException;
import org.apache.commons.validator.routines.EmailValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Validator {

    public static final String UUID_FORMAT = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
    public static final String PHONE_FORMAT = "^\\d{1,16}$";
    public static final String USERNAME_FORMAT = "^([0-9]*[a-z]*[A-Z]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String PASSWORD_FORMAT = "^([0-9]*[a-z]*[A-Z]*[@#$%^&+=]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String NUMBER_FORMAT = "^\\d+$";

    public static void validateUsuario(CreateUsuarioRequest createUsuarioRequest, ErrorOutput errors) throws ValidatorException {
        if (!validateEmail(createUsuarioRequest.getEmail())) {
            errors.addError("Validation Failed", "email", "The format in the field email("
                    + createUsuarioRequest.getEmail() + ") is invalid");
            throw new ValidatorException();
        }
    }

    public static void validateMenu(CreateMenuRequest createMenuRequest, ErrorOutput errors) throws ValidatorException {
        if (!validateUuid(createMenuRequest.getRestauranteUuid()))
            errors.addError("Validation Failed", "restauranteUuid", "The format in the field restauranteUuid(" +
                    createMenuRequest.getRestauranteUuid() + ") is invalid");
        if (!validateMoney(createMenuRequest.getCosto()))
            errors.addError("Validation Failed", "costo", "The format in the field costo("
                    + createMenuRequest.getCosto() + ") is invalid");
        if (errors.hasErrors())
            throw new ValidatorException();
    }

    public static void validateRestaurante(CreateRestauranteRequest createRestauranteRequest, ErrorOutput errors) throws ValidatorException {
        if (!validateUsername(createRestauranteRequest.getUsuario()))
            errors.addError("Validation Failed", "usuario", "The format in the field usuario("
                    + createRestauranteRequest.getUsuario() + ") is invalid");
        if (!validateNumber(createRestauranteRequest.getNumero()))
            errors.addError("Validation Failed", "numero", "The format in the field numero("
                    + createRestauranteRequest.getNumero() + ") is invalid");
        if (!validatePhone(createRestauranteRequest.getTelefono()))
            errors.addError("Validation Failed", "telefono", "The format in the field telefono("
                    + createRestauranteRequest.getTelefono() + ") is invalid");
        if (!validateEmail(createRestauranteRequest.getEmail()))
            errors.addError("Validation Failed", "email", "The format in the field email("
                    + createRestauranteRequest.getEmail() + ") is invalid");
        if (!validatePassword(createRestauranteRequest.getPassword()))
            errors.addError("Validation Failed", "password", "The format in the field password("
                    + createRestauranteRequest.getPassword() + ") is invalid");
        if (errors.hasErrors()) throw new ValidatorException();
    }

    public static void validatePedido(CreatePedidoRequest createPedidoRequest, ErrorOutput errors) throws ValidatorException, InconsistencyException {
        if (!validateCantidades(createPedidoRequest.getCantidades()))
            errors.addError("Validation Failed", "cantidades",
                    "The format in at least one of the values in the field cantidades is invalid");
        if (createPedidoRequest.getMenuUuids().length != createPedidoRequest.getCantidades().length)
            errors.addError("Validation Failed", "menusUuid", "The ammount of menus (" +
                    createPedidoRequest.getMenuUuids().length + ") does not match the ammount of cantidad("
                    + createPedidoRequest.getCantidades().length + ")");
        if (!validateUuid(createPedidoRequest.getDireccionUuid()))
            errors.addError("Validation Failed", "direccionUuid", "The format in the field direccionUuid(" +
                    createPedidoRequest.getDireccionUuid() + ") is invalid");
        if (!validatePhone(createPedidoRequest.getTelefono()))
            errors.addError("Validation Failed", "telefono", "The format in the field telefono("
                    + createPedidoRequest.getTelefono() + ") is invalid");
        if (!validateUuid(createPedidoRequest.getUsuarioUuid()))
            errors.addError("Validation Failed", "usuarioUuid", "The format in the field usuarioUuid("
                    + createPedidoRequest.getUsuarioUuid() + ") is invalid");
        if (!validateUuid(createPedidoRequest.getRestauranteUuid()))
            errors.addError("Validation Failed", "restauranteUuid", "The format in the field restauranteUuid(" +
                    createPedidoRequest.getRestauranteUuid() + ") is invalid");
        if (!validateMenuArray(createPedidoRequest.getMenuUuids()))
            errors.addError("Validation Failed", "menuUuids", "The format in the field menuUuids is invalid");
        if (!validateDate(createPedidoRequest.getFecha()))
            errors.addError("Validation Failed", "fecha", "The recieved date is invalid: "
                    + createPedidoRequest.getFecha().toString());
        if (errors.hasErrors()) throw new ValidatorException();
    }

    public static void validateRestauranteLogin(LoginRestauranteRequest loginRestauranteRequest, ErrorOutput errors) throws ValidatorException {
        if (!validateUsername(loginRestauranteRequest.getUsuario()))
            errors.addError("Validation Failed.", "usuario", "The format in the field usuario("
                    + loginRestauranteRequest.getUsuario() + ") is invalid");
        if (!validatePassword(loginRestauranteRequest.getPassword()))
            errors.addError("Validation Failed.", "password", "The format in the field password("
                    + loginRestauranteRequest.getPassword() + ") is invalid");
        if (errors.hasErrors()) throw new ValidatorException();
    }

    public static void validateAuthenticationToken(AuthenticatedRestauranteToken authenticatedRestauranteToken, ErrorOutput errors) throws ValidatorException {
        if (!validateUuid(authenticatedRestauranteToken.getRestauranteUuid()))
            errors.addError("Validation Failed.", "restauranteUuid", "The format in the field restauranteUuid("
                    + authenticatedRestauranteToken.getRestauranteUuid() + ") is invalid");
        if (!validateUuid(authenticatedRestauranteToken.getToken()))
            errors.addError("Validation Failed.", "token", "The format in the field token("
                    + authenticatedRestauranteToken.getToken() + ") is invalid");
        if (errors.hasErrors()) throw new ValidatorException();
    }

    public static void validateDireccion(CreateDireccionRequest createDireccionRequest, ErrorOutput errors) throws ValidatorException {
        if (!validateUuid(createDireccionRequest.getUsuarioUuid()))
            errors.addError("Validation Failed.", "usuarioUuid", "The format in the field usuarioUuid(" +
                    createDireccionRequest.getUsuarioUuid() + ") is invalid");
        if (!validateNumber(createDireccionRequest.getNumero()))
            errors.addError("Validation Failed.", "numero", "The format in the field numero(" +
                    createDireccionRequest.getNumero() + ") is invalid");
        if (createDireccionRequest.getPiso() == null &&
                createDireccionRequest.getDepartamento() != null)
            errors.addError("Validation Failed.", "piso/departamento",
                    "If piso is null departamento must be null.");
        if (errors.hasErrors()) throw new ValidatorException();
    }

    public static void validatePageNumber(int pagenumber, ErrorOutput errors) throws ValidatorException {
        if (pagenumber < 1) {
            errors.addError("Validation Failed.", "pageNumber",
                    "recieved page number must be equals to 1 or higher");
            throw new ValidatorException();
        }
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
