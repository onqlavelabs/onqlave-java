package com.onqlave.utils;

import com.google.crypto.tink.subtle.XChaCha20Poly1305;

public class XChaCha20Poly1305Helper {
    public static byte[] Encrypt(byte[] key, byte[] plaintext, byte[] associatedData) throws Exception {
        XChaCha20Poly1305 cipher = new XChaCha20Poly1305(key);
        return cipher.encrypt(plaintext, associatedData);
    }

    public static byte[] Decrypt(byte[] key, byte[] cipherText, byte[] associatedData) throws Exception {
        XChaCha20Poly1305 cipher = new XChaCha20Poly1305(key);
        return cipher.decrypt(cipherText, associatedData);
    }
}
