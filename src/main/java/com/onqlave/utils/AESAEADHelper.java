package com.onqlave.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;

import static com.onqlave.utils.Constants.*;

public class AESAEADHelper {
    public static byte[] Encrypt(byte[] plainText, byte[] associatedData, byte[] key, byte[] iv) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        cipher.updateAAD(associatedData);
        byte[] cipherText = cipher.doFinal(plainText);
        return cipherText;
    }

    public static byte[] Decrypt(byte[] cipherText, byte[] associatedData, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(AES_GCM_TAG_SIZE * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), spec);
        cipher.updateAAD(associatedData);
        return cipher.doFinal(cipherText);
    }
}
