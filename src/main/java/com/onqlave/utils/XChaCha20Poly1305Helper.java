package com.onqlave.utils;

import com.google.crypto.tink.subtle.XChaCha20Poly1305;
import com.onqlave.exception.SecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XChaCha20Poly1305Helper {
    private static final Logger LOGGER = LogManager.getLogger();

    public static byte[] Encrypt(byte[] key, byte[] plaintext, byte[] associatedData) throws Exception {
        try {
            XChaCha20Poly1305 cipher = new XChaCha20Poly1305(key);
            return cipher.encrypt(plaintext, associatedData);
        } catch (Exception e) {
            LOGGER.error("Encrypt: ", e);
            throw new SecurityException(e);
        }
    }

    public static byte[] Decrypt(byte[] key, byte[] cipherText, byte[] associatedData) throws Exception {
        try {
            XChaCha20Poly1305 cipher = new XChaCha20Poly1305(key);
            return cipher.decrypt(cipherText, associatedData);
        } catch (Exception e) {
            LOGGER.error("Decrypt: ", e);
            throw new SecurityException(e);
        }
    }
}
