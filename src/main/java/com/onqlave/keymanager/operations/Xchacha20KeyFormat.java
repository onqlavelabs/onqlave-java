package com.onqlave.keymanager.operations;

import com.onqlave.types.KeyFormat;

public class Xchacha20KeyFormat implements KeyFormat {
    public int KeySize;
    public int Version;

    public Xchacha20KeyFormat(int keySize, int version) {
        KeySize = keySize;
        Version = version;
    }

    @Override
    public int Size() {
        return 0;
    }
}
