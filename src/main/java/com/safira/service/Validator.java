package com.safira.service;

import com.safira.common.exceptions.ValidatorException;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by francisco on 21/03/15.
 */
public class Validator {

    public static final String MONEY_FORMAT = "^\\d+\\.\\d+$";
    public static final String UUID_FORMAT = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
    public static final String PHONE_FORMAT = "^\\d{1,16}$";
    public static final String USERNAME_FORMAT = "^([0-9]*[a-z]*[A-Z]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String PASSWORD_FORMAT = "^([0-9]*[a-z]*[A-Z]*[@#$%^&+=]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String NUMBER_FORMAT = "^\\d{1,5}$";

    public static void validateUsuario(String email) throws ValidatorException {
        if (!validateEmail(email))
            throw new ValidatorException("The format in the field email(" + email + ") is invalid");

    }

    public static void validateMenu(String costo, String restauranteUuid) throws ValidatorException {
        if (!validateUuid(restauranteUuid))
            throw new ValidatorException("The format in the field restauranteUuid(" + restauranteUuid + ") is invalid");
        if (!validateMoney(costo))
            throw new ValidatorException("The format in the field restauranteUUid(" + costo + ") is invalid");

    }

    public static void validateRestaurante(String usuario,
                                           String password,
                                           String numero,
                                           String telefono,
                                           String email) throws ValidatorException {
        if (!validateUsername(usuario))
            throw new ValidatorException("The format in the field usuario(" + usuario + ") is invalid");
        if (!validateNumber(numero))
            throw new ValidatorException("The format in the field numero(" + numero + ") is invalid");
        if (!validatePhone(telefono))
            throw new ValidatorException("The format in the field telefono(" + telefono + ") is invalid");
        if (!validateEmail(email))
            throw new ValidatorException("The format in the field email(" + email + ") is invalid");
        if (!validatePassword(password))
            throw new ValidatorException("The format in the field password(" + password + ") is invalid");
    }

    public static void validatePedido(String direccionUuid,
                                      String telefono,
                                      String usuarioUuid,
                                      String restauranteUuid,
                                      String[] menuUuids) throws ValidatorException {
        if (!validateUuid(direccionUuid))
            throw new ValidatorException("The format in the field direccionUuid(" + direccionUuid + ") is invalid");
        if (!validatePhone(telefono))
            throw new ValidatorException("The format in the field telefono(" + telefono + ") is invalid");
        if (!validateUuid(usuarioUuid))
            throw new ValidatorException("The format in the field usuarioUuid(" + usuarioUuid + ") is invalid");
        if (!validateUuid(restauranteUuid))
            throw new ValidatorException("The format in the field restauranteUuid(" + restauranteUuid + ") is invalid");
        if (!validateMenuArray(menuUuids))
            throw new ValidatorException("The format in the field menuUuids is invalid");
    }

    private static boolean validateMenuArray(String[] menuUuids) {
        for (String uuid : menuUuids) {
            if (!validateUuid(uuid)) return false;
        }
        return true;
    }

    private static boolean validateMoney(String money) {
        return money.matches(MONEY_FORMAT);
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
}
