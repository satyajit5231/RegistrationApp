package com.util;

public class PasswordUtil {

    // Minimum 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char
    private static final String STRONG_PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public static boolean isStrong(String password) {

        if (password == null) {
            return false;
        }

        return password.matches(STRONG_PASSWORD_REGEX);
    }
}
