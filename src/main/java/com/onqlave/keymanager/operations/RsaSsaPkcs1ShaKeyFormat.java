package com.onqlave.keymanager.operations;

import com.onqlave.types.HashType;
import com.onqlave.types.KeyFormat;

public class RsaSsaPkcs1ShaKeyFormat implements KeyFormat {
    private final int version;
    private final HashType hashType;

    public RsaSsaPkcs1ShaKeyFormat(HashType hashType, int version) {
        this.hashType = hashType;
        this.version = version;
    }

    @Override
    public int size() {
        return 0;
    }

    public HashType getHashType() {
        return hashType;
    }
}
