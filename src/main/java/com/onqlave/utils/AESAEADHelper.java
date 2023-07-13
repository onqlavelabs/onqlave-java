package com.onqlave.utils;

import com.onqlave.exception.SecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

import static com.onqlave.utils.Constants.*;

public class AESAEADHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    public static byte[] Encrypt(byte[] plainText, byte[] associatedData, byte[] key, byte[] iv) throws SecurityException {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            byte[] aad = associatedData;
            if (associatedData == null) {
                aad = new byte[0];
            }
            cipher.updateAAD(aad);
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            LOGGER.error("Encrypt: ", e);
            throw new SecurityException(e);
        }
    }

    public static byte[] Decrypt(byte[] cipherText, byte[] associatedData, byte[] key, byte[] iv) throws SecurityException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(AES_GCM_TAG_SIZE * 8, iv);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), spec);
            byte[] aad = associatedData;
            if (associatedData == null) {
                aad = new byte[0];
            }
            cipher.updateAAD(aad);
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            LOGGER.error("Decrypt: ", e);
            throw new SecurityException(e);
        }
    }
}