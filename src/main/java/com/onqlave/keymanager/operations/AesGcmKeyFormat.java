package com.onqlave.keymanager.operations;

import com.onqlave.types.KeyFormat;

public class AesGcmKeyFormat implements KeyFormat {
    public int keySize;
    public int version;

    public AesGcmKeyFormat(int keySize, int version) {
        this.keySize = keySize;
        this.version = version;

    }

    @Override
    public int Size() {
        return this.keySize;
    }
}
