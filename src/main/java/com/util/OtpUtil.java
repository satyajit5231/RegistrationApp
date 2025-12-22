package com.util;

import java.security.SecureRandom;

public class OtpUtil {

    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    // Generate 6-digit numeric OTP
    public static int generateOtp() {
        int min = (int) Math.pow(10, OTP_LENGTH - 1); // 100000
        int max = (int) Math.pow(10, OTP_LENGTH) - 1; // 999999
        return random.nextInt(max - min + 1) + min;
    }
}
