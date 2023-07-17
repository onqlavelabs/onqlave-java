package com.onqlave.keymanager.operations;

import com.onqlave.types.KeyFactory;
import com.onqlave.types.KeyFormat;
import com.onqlave.types.KeyOperation;

public class Aes256GcmOperation implements KeyOperation {
    private KeyFactory factory;
    private AesGcmKeyFormat format;
    public static final int AES256_GCM_KEY_VERSION = 0;
    public static final int AES256_GCM_KEY_SIZE = 32;

    public Aes256GcmOperation(KeyFactory factory) {
        this.factory = factory;
        this.format = new AesGcmKeyFormat(AES256_GCM_KEY_SIZE, AES256_GCM_KEY_VERSION);
    }

    @Override
    public KeyFormat getFormat() {
        return this.format;
    }

    @Override
    public KeyFactory getFactory() {
        return this.factory;
    }

}
