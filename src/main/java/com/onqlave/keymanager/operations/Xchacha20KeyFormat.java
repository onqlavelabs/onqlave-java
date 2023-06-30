package com.onqlave.keymanager.operations;

import com.onqlave.types.KeyFormat;

public class Xchacha20KeyFormat implements KeyFormat {
    public int keySize;
    public int version;

    public Xchacha20KeyFormat(int keySize, int version) {
        this.keySize = keySize;
        this.version = version;
    }

    @Override
    public int Size() {
        return this.keySize;
    }
}
