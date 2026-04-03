package com.land.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final long OTP_EXPIRY_SECONDS = 600; // 10 minutes
    private static final SecureRandom RANDOM = new SecureRandom();

    // email -> [otp, expiryEpochSecond]
    private final Map<String, long[]> otpStore = new ConcurrentHashMap<>();

    /** Generate a 6-digit OTP, store it, and return it. */
    public String generateOtp(String email) {
        int code = 100000 + RANDOM.nextInt(900000); // 100000–999999
        long expiry = Instant.now().getEpochSecond() + OTP_EXPIRY_SECONDS;
        otpStore.put(email.toLowerCase(), new long[]{code, expiry});
        return String.valueOf(code);
    }

    /** Returns true if the supplied OTP is correct and not expired. Removes it on success. */
    public boolean validateOtp(String email, String otp) {
        long[] record = otpStore.get(email.toLowerCase());
        if (record == null) return false;

        long storedCode  = record[0];
        long expiryEpoch = record[1];

        boolean expired = Instant.now().getEpochSecond() > expiryEpoch;
        boolean match   = String.valueOf(storedCode).equals(otp.trim());

        if (match && !expired) {
            otpStore.remove(email.toLowerCase()); // single-use
            return true;
        }
        return false;
    }

    /** Remove any stored OTP for the given email (e.g. on resend). */
    public void clearOtp(String email) {
        otpStore.remove(email.toLowerCase());
    }
}
