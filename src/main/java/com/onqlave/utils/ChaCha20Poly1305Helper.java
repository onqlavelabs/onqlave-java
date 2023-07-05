package com.onqlave.utils;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.macs.Poly1305;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import static com.onqlave.utils.Constants.XChaCha20Poly1305NonceSize;
import static com.onqlave.utils.Constants.XChaCha20Poly1305TagSize;

public class ChaCha20Poly1305Helper {
    public static byte[] Encrypt(byte[] plaintext, byte[] key, byte[] nonce) throws Exception {
//        ChaChaEngine cipher = new ChaChaEngine();
//        cipher.init(true, new ParametersWithIV(new KeyParameter(key), nonce, 0, 8));
//        byte[] polyKey = new byte[32];
//        cipher.processBytes(new byte[16], 0, 16, polyKey, 0);
//
//        // Encrypt the plaintext using the ChaCha20 cipher
//        byte[] ciphertext = new byte[plaintext.length];
//        cipher.processBytes(plaintext, 0, plaintext.length, ciphertext, 0);
//
//        // Generate the Poly1305 tag
//        Poly1305 mac = new Poly1305();
//        mac.init(new KeyParameter(polyKey));
//        mac.update(ciphertext, 0, ciphertext.length);
//        byte[] tag = new byte[16];
//        mac.doFinal(tag, 0);
//
//        // Concatenate the ciphertext and the Poly1305 tag
//        byte[] encryptedData = new byte[ciphertext.length + tag.length];
//        System.arraycopy(ciphertext, 0, encryptedData, 0, ciphertext.length);
//        System.arraycopy(tag, 0, encryptedData, ciphertext.length, tag.length);
//        return encryptedData;

        Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305/None/NoPadding");

        // Create IvParamterSpec
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(nonce);

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key, "ChaCha20");

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

        // Perform Encryption
        return cipher.doFinal(plaintext);
    }

    public static byte[] Decrypt(byte[] cipherText, byte[] key) throws Exception {
        if (cipherText.length < XChaCha20Poly1305NonceSize + XChaCha20Poly1305TagSize) {
            throw new Exception("Ciphertext too short");
        }
        byte[] nonce = Arrays.copyOfRange(cipherText, 0, XChaCha20Poly1305NonceSize);
        byte[] encryptedData = Arrays.copyOfRange(cipherText, XChaCha20Poly1305NonceSize, cipherText.length);
        byte[] plaintext;
        try {
            Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305/None/NoPadding");
//            GCMParameterSpec spec = new GCMParameterSpec(XChaCha20Poly1305TagSize * 8, nonce);

            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(nonce);
            SecretKeySpec keySpec = new SecretKeySpec(key, "ChaCha20");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
//            cipher.updateAAD(associatedData);
            plaintext = cipher.doFinal(encryptedData);
        } catch (AEADBadTagException e) {
            throw new Exception("Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Decryption failed: " + e.getMessage());
        }
        return plaintext;

        // Split the encrypted data into ciphertext and tag
//        int ciphertextLength = encryptedData.length - 16 - 24;
//        byte[] ciphertext = new byte[ciphertextLength];
//        byte[] tag = new byte[16];
//
//        byte[] nonce = new byte[24];
//        System.arraycopy(encryptedData, 0, nonce, 0, 24);
//        System.arraycopy(encryptedData, 24, ciphertext, 0, ciphertextLength);
//        System.arraycopy(encryptedData, ciphertextLength + 24, tag, 0, 16);
//
////        System.arraycopy(encryptedData, 0, ciphertext, 0, ciphertextLength);
////        System.arraycopy(encryptedData, ciphertextLength, tag, 0, 16);
//
//        // Create a ChaCha20-Poly1305 cipher instance
//        ChaChaEngine cipher = new ChaChaEngine();
//        cipher.init(true, new ParametersWithIV(new KeyParameter(key), nonce, 0, 8));
//        byte[] polyKey = new byte[32];
//        cipher.processBytes(new byte[16], 0, 16, polyKey, 0);
//
//        // Verify the Poly1305 tag
//        Poly1305 mac = new Poly1305();
//        mac.init(new KeyParameter(polyKey));
//        mac.update(ciphertext, 0, ciphertext.length);
//        byte[] calculatedTag = new byte[16];
//        mac.doFinal(calculatedTag, 0);
//
//        // Compare the calculated tag with the received tag
//        if (!constantTimeEquals(tag, calculatedTag)) {
//            throw new Exception("MAC check failed. The ciphertext may have been tampered with.");
//        }
//
//        // Decrypt the ciphertext using the ChaCha20 cipher
//        byte[] decryptedText = new byte[ciphertextLength];
//        cipher.processBytes(ciphertext, 0, ciphertextLength, decryptedText, 0);
//
//        return decryptedText;
    }

    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }

        return result == 0;
    }

}
