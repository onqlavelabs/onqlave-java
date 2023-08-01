package com.onqlave.keymanager.operations;

import com.onqlave.types.KeyFormat;

public class AesGcmKeyFormat implements KeyFormat {
    private int keySize;
    private int version;

    public AesGcmKeyFormat(int keySize, int version) {
        this.keySize = keySize;
        this.version = version;
    }

    @Override
    public int size() {
        return this.keySize;
    }
}
