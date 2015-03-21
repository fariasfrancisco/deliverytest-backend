package com.safira.service;

import com.safira.common.Regex;

/**
 * Created by francisco on 21/03/15.
 */
public class Validator {

    public static boolean validateUsuario(String email) {
        return validateEmail(email);
    }

    public static boolean validateMenu(String costo, String restauranteUuid) {
        return validateMoney(costo) && validateUuid(restauranteUuid);
    }

    public static boolean validateRestaurante(String usuario,
                                              String password,
                                              String numero,
                                              String telefono,
                                              String email) {
        return validateUsername(usuario) &&
                validateNumber(numero) &&
                validatePhone(telefono) &&
                validateEmail(email) &&
                validatePassword(password);
    }

    public static boolean validatePedido(String numero,
                                         String telefono,
                                         String usuarioUuid,
                                         String restauranteUUid,
                                         String[] menuUuids) {
        return validateNumber(numero) &&
                validatePhone(telefono) &&
                validateUuid(usuarioUuid) &&
                validateUuid(restauranteUUid) &&
                validateMenuArray(menuUuids);
    }

    private static boolean validateMenuArray(String[] menuUuids) {
        for (String uuid : menuUuids) {
            if (!validateUuid(uuid)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateMoney(String money) {
        return money.matches(Regex.MONEY_FORMAT);
    }

    private static boolean validateUuid(String uuid) {
        return uuid.matches(Regex.UUID_FORMAT);
    }

    private static boolean validatePhone(String phone) {
        return phone.matches(Regex.PHONE_FORMAT);
    }

    private static boolean validateUsername(String username) {
        return username.matches(Regex.USERNAME_FORMAT);
    }

    private static boolean validatePassword(String password) {
        return password.matches(Regex.PASSWORD_FORMAT);
    }

    private static boolean validateNumber(String number) {
        return number.matches(Regex.NUMBER_FORMAT);
    }

    private static boolean validateEmail(String email) {
        return email.matches(Regex.EMAIL_FORMAT);
    }
}
