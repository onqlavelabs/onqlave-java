package com.onqlave.keymanager.operations;

import com.onqlave.types.KeyFormat;

public class AesGcmKeyFormat implements KeyFormat {
    public int KeySize;
    public int Version;

    public AesGcmKeyFormat(int keySize, int version) {
        this.KeySize = keySize;
        this.Version = version;

    }

    @Override
    public int Size() {
        return 0;
    }
}
