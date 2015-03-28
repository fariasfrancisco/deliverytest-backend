package com.safira.service;

import com.safira.api.*;
import com.safira.common.exceptions.ValidatorException;
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
            throw new ValidatorException("The format in the field email("
                    + createUsuarioRequest.getEmail() + ") is invalid");
    }

    public static void validateMenu(CreateMenuRequest createMenuRequest) throws ValidatorException {
        if (!validateUuid(createMenuRequest.getRestauranteUuid()))
            throw new ValidatorException("The format in the field restauranteUuid(" +
                    createMenuRequest.getRestauranteUuid() + ") is invalid");
        if (!validateMoney(createMenuRequest.getCosto()))
            throw new ValidatorException("The format in the field restauranteUUid(" +
                    createMenuRequest.getCosto() + ") is invalid");
    }

    public static void validateRestaurante(CreateRestauranteRequest createRestauranteRequest) throws ValidatorException {
        if (!validateUsername(createRestauranteRequest.getUsuario()))
            throw new ValidatorException("The format in the field usuario(" +
                    createRestauranteRequest.getUsuario() + ") is invalid");
        if (!validateNumber(createRestauranteRequest.getNumero()))
            throw new ValidatorException("The format in the field numero(" +
                    createRestauranteRequest.getNumero() + ") is invalid");
        if (!validatePhone(createRestauranteRequest.getTelefono()))
            throw new ValidatorException("The format in the field telefono(" +
                    createRestauranteRequest.getTelefono() + ") is invalid");
        if (!validateEmail(createRestauranteRequest.getEmail()))
            throw new ValidatorException("The format in the field email(" +
                    createRestauranteRequest.getEmail() + ") is invalid");
        if (!validatePassword(createRestauranteRequest.getPassword()))
            throw new ValidatorException("The format in the field password(" +
                    createRestauranteRequest.getPassword() + ") is invalid");
    }

    public static void validatePedido(CreatePedidoRequest createPedidoRequest) throws ValidatorException {
        if (!validateUuid(createPedidoRequest.getDireccionUuid()))
            throw new ValidatorException("The format in the field direccionUuid(" +
                    createPedidoRequest.getDireccionUuid() + ") is invalid");
        if (!validatePhone(createPedidoRequest.getTelefono()))
            throw new ValidatorException("The format in the field telefono(" +
                    createPedidoRequest.getTelefono() + ") is invalid");
        if (!validateUuid(createPedidoRequest.getUsuarioUuid()))
            throw new ValidatorException("The format in the field usuarioUuid(" +
                    createPedidoRequest.getUsuarioUuid() + ") is invalid");
        if (!validateUuid(createPedidoRequest.getRestauranteUuid()))
            throw new ValidatorException("The format in the field restauranteUuid(" +
                    createPedidoRequest.getRestauranteUuid() + ") is invalid");
        if (!validateMenuArray(createPedidoRequest.getMenuUuids()))
            throw new ValidatorException("The format in the field menuUuids is invalid");
        if (!validateDate(createPedidoRequest.getFecha()))
            throw new ValidatorException("The date in the field fecha is invalid");
    }

    public static void validateRestauranteLogin(LoginRestauranteRequest loginRestauranteRequest) throws ValidatorException {
        if (!validateUsername(loginRestauranteRequest.getUsuario()))
            throw new ValidatorException("The format in the field usuario(" +
                    loginRestauranteRequest.getUsuario() + ") is invalid");
        if (!validatePassword(loginRestauranteRequest.getPassword()))
            throw new ValidatorException("The format in the field password(" +
                    loginRestauranteRequest.getPassword() + ") is invalid");
    }

    public static void validateDireccion(CreateDireccionRequest createDireccionRequest) throws ValidatorException {
        if (!validateNumber(createDireccionRequest.getNumero()))
            throw new ValidatorException("The format in the field numero(" +
                    createDireccionRequest.getNumero() + ") is invalid");
        if (createDireccionRequest.getPiso() == null)
            if (createDireccionRequest.getDepartamento() != null)
                throw new ValidatorException("If piso is null departamento must be null.");
    }

    private static boolean validateMenuArray(String[] menuUuids) {
        for (String uuid : menuUuids) {
            if (!validateUuid(uuid)) return false;
        }
        return true;
    }

    private static boolean validateMoney(BigDecimal money) {
        return (money.scale() > 2) && (money.signum() == 1);
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

    private static boolean validateDate(LocalDateTime date){return date.isBefore(LocalDateTime.now());}
}
