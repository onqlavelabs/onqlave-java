package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;

public class AesGcmAead implements AEAD {
    public static final int AES_GCM_IV_SIZE = 12;
    public static final int AES_GCM_TAG_SIZE = 16;

    private CPRNGService randomService;
    private byte[] key;
    private Boolean prependIV;

    public AesGcmAead(CPRNGService randomService, Key key) {
        //TODO:fill parameter
        this.randomService = randomService;
    }

    @Override
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] Decrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return new byte[0];
    }
}
