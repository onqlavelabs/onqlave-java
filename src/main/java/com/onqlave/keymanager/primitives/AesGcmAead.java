package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;
import com.onqlave.types.KeyData;
import com.onqlave.utils.AESAEADHelper;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.onqlave.utils.Constants.*;

public class AesGcmAead implements AEAD {


    private CPRNGService randomService;
    private Key key;
    private Boolean prependIV;

    //TODO: consider review this constructor
    public AesGcmAead(Key key, CPRNGService randomService) throws Exception {
        //TODO: need to review again
        this.randomService = randomService;
        this.key = key;
        this.prependIV = true;
    }

    @Override
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        byte[] iv = randomService.GetRandomBytes(AES_GCM_IV_SIZE);
        byte[] cipherText = AESAEADHelper.Encrypt(plaintext, associatedData, this.key.Data().GetValue(), iv);
        if (prependIV) {
            byte[] cipherTextWithIV = new byte[cipherText.length + iv.length];
            System.arraycopy(iv, 0, cipherTextWithIV, 0, iv.length);
            System.arraycopy(cipherText, 0, cipherTextWithIV, iv.length, cipherText.length);
            return cipherTextWithIV;
        }
        return cipherText;
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

        return AESAEADHelper.Decrypt(actualCipherText, associatedData, this.key.Data().GetValue(), iv);
    }

    public static boolean validateKeySize(int sizeInBytes) {
        switch (sizeInBytes) {
            case 16, 32:
                return true;
            default:
                return false;
        }
    }
}
