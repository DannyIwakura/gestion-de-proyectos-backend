package com.gruposolutia.gestion_proyectos.service;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    private static final String ISSUER = "GestionProyectos";
    private static final int TOTP_DIGITS = 6;
    private static final int TOTP_INTERVAL = 30;
    private static final int WINDOW_SIZE = 3;
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private final SecureRandom secureRandom = new SecureRandom();

    public TotpSetup generateSecret(String username) {
        byte[] buffer = new byte[20];
        secureRandom.nextBytes(buffer);

        String secret = new Base32().encodeToString(buffer);

        String qrCodeUri = String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=%d&period=%d",
                ISSUER, username, secret, ISSUER, TOTP_DIGITS, TOTP_INTERVAL);

        return new TotpSetup(secret, qrCodeUri);
    }

    public boolean verifyCode(String secret, int verificationCode) {
        long time = Instant.now().getEpochSecond() / TOTP_INTERVAL;
        return checkCode(secret, verificationCode, time, WINDOW_SIZE);
    }

    private boolean checkCode(String secret, int code, long time, int window) {
        byte[] decodedKey = new Base32().decode(secret);

        for (int i = -window; i <= window; i++) {
            long candidate = calculateCode(decodedKey, time + i);
            if (candidate == code) {
                return true;
            }
        }
        return false;
    }

    private long calculateCode(byte[] key, long time) {
        byte[] data = ByteBuffer.allocate(8).putLong(time).array();

        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(key, HMAC_ALGORITHM));
            byte[] hash = mac.doFinal(data);

            int offset = hash[hash.length - 1] & 0xF;
            int binary = ((hash[offset] & 0x7F) << 24)
                    | ((hash[offset + 1] & 0xFF) << 16)
                    | ((hash[offset + 2] & 0xFF) << 8)
                    | (hash[offset + 3] & 0xFF);

            return binary % (int) Math.pow(10, TOTP_DIGITS);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error calculating TOTP code", e);
        }
    }

    public static class TotpSetup {
        private final String secret;
        private final String qrCodeUri;

        public TotpSetup(String secret, String qrCodeUri) {
            this.secret = secret;
            this.qrCodeUri = qrCodeUri;
        }

        public String getSecret() {
            return secret;
        }

        public String getQrCodeUri() {
            return qrCodeUri;
        }
    }
}
