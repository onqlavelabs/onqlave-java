package com.onqlave.keymanager.operations;

import com.onqlave.types.KeyFactory;
import com.onqlave.types.KeyFormat;
import com.onqlave.types.KeyOperation;

public class Aes128GcmOperation implements KeyOperation {
    private KeyFactory factory;
    private AesGcmKeyFormat format;

    public static final int AES128_GCM_KEY_VERSION = 0;
    public static final int AES128_GCM_KEY_SIZE = 16;

    public Aes128GcmOperation(KeyFactory factory) {
        AesGcmKeyFormat format = new AesGcmKeyFormat(AES128_GCM_KEY_SIZE, AES128_GCM_KEY_VERSION);
        this.factory = factory;
        this.format = format;
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
