package com.onqlave.keymanager.operations;

import com.onqlave.types.HashType;
import com.onqlave.types.KeyFormat;

public class RsaSsaPkcs1ShaKeyFormat implements KeyFormat {
    public int Version;
    public HashType HashType;

    public RsaSsaPkcs1ShaKeyFormat(HashType hashType, int version) {
        this.HashType = hashType;
        this.Version = version;
    }

    @Override
    public int Size() {
        return 0;
    }
}
