package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;
import com.onqlave.types.KeyData;

public class AesGcmAead implements AEAD {
    public static final int AES_GCM_IV_SIZE = 12;
    public static final int AES_GCM_TAG_SIZE = 16;

    private CPRNGService randomService;
    private byte[] key;
    private Boolean prependIV;

    //TODO: consider review this constructor
    public AesGcmAead(Key key, CPRNGService randomService) throws Exception {
        KeyData keyData = key.Data();

        //TODO: consider to add try/catch block here if you want to handle exception
        byte[] keyValue = keyData.GetValue();
        if (validateKeySize(keyValue.length)) {
            throw new Exception("invalid AES key size");
        }

        //TODO: need to review again
        this.randomService =randomService;
        this.key = keyValue;
        this.prependIV = true;
    }

    @Override
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] Decrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return new byte[0];
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
