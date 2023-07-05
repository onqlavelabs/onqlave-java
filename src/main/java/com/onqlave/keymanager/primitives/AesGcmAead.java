package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;
import com.onqlave.types.KeyData;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.onqlave.utils.Constants.*;

public class AesGcmAead implements AEAD {


    private CPRNGService randomService;
    private byte[] key;
    private Boolean prependIV;

    //TODO: consider review this constructor
    public AesGcmAead(Key key, CPRNGService randomService) throws Exception {
        KeyData keyData = key.Data();

        //TODO: consider to add try/catch block here if you want to handle exception
        byte[] keyValue = keyData.GetValue();
        if (!validateKeySize(keyValue.length)) {
            throw new Exception("invalid AES key size");
        }

        //TODO: need to review again
        this.randomService = randomService;
        this.key = keyValue;
        this.prependIV = true;
    }

    @Override
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        byte[] iv = randomService.GetRandomBytes(AES_GCM_IV_SIZE);
        if (iv.length != AES_GCM_IV_SIZE) {
            throw new Exception(String.format("unexpected IV size: got %d, want % d", iv.length, AES_GCM_IV_SIZE));
        }
        Cipher cipher = newCipher();
        GCMParameterSpec spec = new GCMParameterSpec(AES_GCM_TAG_SIZE * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), spec);

        byte[] ciphertext = cipher.doFinal(plaintext);

        if (prependIV) {
            byte[] ivAndCiphertext = new byte[AES_GCM_IV_SIZE + ciphertext.length];
            System.arraycopy(iv, 0, ivAndCiphertext, 0, iv.length);
            System.arraycopy(ciphertext, 0, ivAndCiphertext, AES_GCM_IV_SIZE, ciphertext.length);
            return ivAndCiphertext;
        } else {
            return ciphertext;
        }
    }

    @Override
    public byte[] Decrypt(byte[] cipherText, byte[] associatedData) throws Exception {
        if (cipherText.length < AES_GCM_IV_SIZE) {
            throw new IllegalArgumentException(String.format("cipherText with size %d is too short", cipherText.length));
        }
        byte[] iv = Arrays.copyOfRange(cipherText, 0, AES_GCM_IV_SIZE);
        if (iv.length != AES_GCM_IV_SIZE) {
            throw new IllegalArgumentException(String.format("unexpected IV size got %d,want %d", cipherText.length, AES_GCM_IV_SIZE));
        }
        byte[] actualCipherText;
        if (prependIV) {
            if (cipherText.length < MIN_PREPEND_IV_CIPHERTEXT_SIZE) {
                throw new IllegalArgumentException(String.format("cipherTex too short: got %d , want >= %d ", cipherText.length, MIN_PREPEND_IV_CIPHERTEXT_SIZE));
            }
            actualCipherText = Arrays.copyOfRange(cipherText, AES_GCM_IV_SIZE, cipherText.length);

        } else {
            if (cipherText.length < MIN_NO_IV_CIPHERTEXT_SIZE) {
                throw new IllegalArgumentException(String.format("cipherTex too short: got %d , want >= %d ", cipherText.length, MIN_NO_IV_CIPHERTEXT_SIZE));
            }
            actualCipherText = cipherText;
        }

        Cipher cipher = newCipher();
        GCMParameterSpec spec = new GCMParameterSpec(AES_GCM_TAG_SIZE * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), spec);
        return cipher.doFinal(actualCipherText);

    }

    public static boolean validateKeySize(int sizeInBytes) {
        switch (sizeInBytes) {
            case 16, 32:
                return true;
            default:
                return false;
        }
    }

    private Cipher newCipher() throws NoSuchAlgorithmException, GeneralSecurityException {
        return Cipher.getInstance("AES/GCM/NoPadding");
    }
}
