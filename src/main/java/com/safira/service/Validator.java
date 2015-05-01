package com.safira.service;

import com.safira.api.*;
import com.safira.common.ErrorDescription;
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
            String field = "email";
            String message = "The format in the field email(" + createUsuarioRequest.getEmail() + ") is invalid";
            ErrorDescription error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            errors.setMessage("Validation Failed");
            throw new ValidatorException();
        }
    }

    public static void validateMenu(CreateMenuRequest createMenuRequest, ErrorOutput errors) throws ValidatorException {
        String message;
        String field;
        ErrorDescription error;
        if (!validateUuid(createMenuRequest.getRestauranteUuid())) {
            field = "restauranteUuid";
            message = "The format in the field restauranteUuid(" +
                    createMenuRequest.getRestauranteUuid() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateMoney(createMenuRequest.getCosto())) {
            field = "costo";
            message = "The format in the field costo(" + createMenuRequest.getCosto() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            errors.setMessage("Validation Failed");
            throw new ValidatorException();
        }
    }

    public static void validateRestaurante(CreateRestauranteRequest createRestauranteRequest, ErrorOutput errors) throws ValidatorException {
        String message;
        String field;
        ErrorDescription error;
        if (!validateUsername(createRestauranteRequest.getUsuario())) {
            field = "usuario";
            message = "The format in the field usuario(" + createRestauranteRequest.getUsuario() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateNumber(createRestauranteRequest.getNumero())) {
            field = "numero";
            message = "The format in the field numero(" + createRestauranteRequest.getNumero() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validatePhone(createRestauranteRequest.getTelefono())) {
            field = "telefono";
            message = "The format in the field telefono(" + createRestauranteRequest.getTelefono() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateEmail(createRestauranteRequest.getEmail())) {
            field = "email";
            message = "The format in the field email(" + createRestauranteRequest.getEmail() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validatePassword(createRestauranteRequest.getPassword())) {
            field = "password";
            message = "The format in the field password(" + createRestauranteRequest.getPassword() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            errors.setMessage("Validation Failed");
            throw new ValidatorException();
        }
    }

    public static void validatePedido(CreatePedidoRequest createPedidoRequest, ErrorOutput errors) throws ValidatorException, InconsistencyException {
        String message;
        String field;
        ErrorDescription error;
        if (!validateCantidades(createPedidoRequest.getCantidades())) {
            field = "cantidades";
            message = "The format in at least one of the values in the field cantidades is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (createPedidoRequest.getMenuUuids().length != createPedidoRequest.getCantidades().length) {
            field = "menusUuid";
            message = "The ammount of menus (" + createPedidoRequest.getMenuUuids().length
                    + ") does not match the ammount of cantidad(" + createPedidoRequest.getCantidades().length + ")";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateUuid(createPedidoRequest.getDireccionUuid())) {
            field = "direccionUuid";
            message = "The format in the field direccionUuid(" +
                    createPedidoRequest.getDireccionUuid() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validatePhone(createPedidoRequest.getTelefono())) {
            field = "telefono";
            message = "The format in the field telefono(" + createPedidoRequest.getTelefono() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateUuid(createPedidoRequest.getUsuarioUuid())) {
            field = "usuarioUuid";
            message = "The format in the field usuarioUuid(" + createPedidoRequest.getUsuarioUuid() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateUuid(createPedidoRequest.getRestauranteUuid())) {
            field = "restauranteUuid";
            message = "The format in the field restauranteUuid(" +
                    createPedidoRequest.getRestauranteUuid() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateMenuArray(createPedidoRequest.getMenuUuids())) {
            field = "menuUuids";
            message = "The format in the field menuUuids is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateDate(createPedidoRequest.getFecha())) {
            field = "fecha";
            message = "The recieved date is invalid: " + createPedidoRequest.getFecha().toString();
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            errors.setMessage("Validation Failed");
            throw new ValidatorException();
        }
    }

    public static void validateRestauranteLogin(LoginRestauranteRequest loginRestauranteRequest, ErrorOutput errors) throws ValidatorException {
        String message;
        String field;
        ErrorDescription error;
        if (!validateUsername(loginRestauranteRequest.getUsuario())) {
            field = "usuario";
            message = "The format in the field usuario(" + loginRestauranteRequest.getUsuario() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validatePassword(loginRestauranteRequest.getPassword())) {
            field = "password";
            message = "The format in the field password(" + loginRestauranteRequest.getPassword() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            errors.setMessage("Validation Failed");
            throw new ValidatorException();
        }
    }

    public static void validateDireccion(CreateDireccionRequest createDireccionRequest, ErrorOutput errors) throws ValidatorException {
        String message;
        String field;
        ErrorDescription error;
        if (!validateUuid(createDireccionRequest.getUsuarioUuid())) {
            field = "usuarioUuid";
            message = "The format in the field usuarioUuid(" +
                    createDireccionRequest.getUsuarioUuid() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (!validateNumber(createDireccionRequest.getNumero())) {
            field = "numero";
            message = "The format in the field numero(" +
                    createDireccionRequest.getNumero() + ") is invalid";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (createDireccionRequest.getPiso() == null &&
                createDireccionRequest.getDepartamento() != null) {
            field = "piso/departamento";
            message = "If piso is null departamento must be null.";
            error = new ErrorDescription(field, message);
            errors.addError(error);
        }
        if (errors.hasErrors()) {
            errors.setMessage("Validation Failed");
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
