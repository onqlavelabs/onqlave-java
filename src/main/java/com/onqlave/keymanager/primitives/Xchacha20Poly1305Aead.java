package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import java.util.Arrays;

import static com.onqlave.utils.Constants.*;

public class Xchacha20Poly1305Aead implements AEAD {
    private CPRNGService randomService;
    private Key key;
    private boolean prependIV;

    public Xchacha20Poly1305Aead(CPRNGService randomService, Key key, boolean prependIV) {
        this.randomService = randomService;
        this.key = key;
        this.prependIV = prependIV;
    }

    @Override
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        byte[] nonce = generateNonce();
        byte[] ciphertext;
        try {
            Cipher cipher = Cipher.getInstance("XChaCha20-Poly1305/None/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(XChaCha20Poly1305TagSize * 8, nonce);
            SecretKeySpec keySpec = new SecretKeySpec(key.Data().GetValue(), "XChaCha20Poly1305");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
            cipher.updateAAD(associatedData);
            ciphertext = cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new Exception("Encryption failed: " + e.getMessage());
        }
        return concatenateByteArrays(nonce, ciphertext);
    }

    @Override
    public byte[] Decrypt(byte[] cipherText, byte[] associatedData) throws Exception {
        if (cipherText.length < XChaCha20Poly1305NonceSize + XChaCha20Poly1305TagSize) {
            throw new Exception("Ciphertext too short");
        }
        byte[] nonce = Arrays.copyOfRange(cipherText, 0, XChaCha20Poly1305NonceSize);
        byte[] encryptedData = Arrays.copyOfRange(cipherText, XChaCha20Poly1305NonceSize, cipherText.length);
        byte[] plaintext;
        try {
            Cipher cipher = Cipher.getInstance("XChaCha20-Poly1305/None/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(XChaCha20Poly1305TagSize * 8, nonce);
            SecretKeySpec keySpec = new SecretKeySpec(key.Data().GetValue(), "XChaCha20Poly1305");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
            cipher.updateAAD(associatedData);
            plaintext = cipher.doFinal(encryptedData);
        } catch (AEADBadTagException e) {
            throw new Exception("Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Decryption failed: " + e.getMessage());
        }
        return plaintext;
    }

    private byte[] generateNonce() {
        return randomService.GetRandomBytes(XChaCha20Poly1305NonceSize);
    }

    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
