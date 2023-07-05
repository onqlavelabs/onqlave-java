package com.onqlave.keymanager.operations;

import com.onqlave.types.HashType;
import com.onqlave.types.KeyFormat;

public class RsaSsaPkcs1ShaKeyFormat implements KeyFormat {
    private int version;
    private HashType hashType;

    public RsaSsaPkcs1ShaKeyFormat(HashType hashType, int version) {
        this.hashType = hashType;
        this.version = version;
    }

    @Override
    public int Size() {
        return 0;
    }

    public HashType getHashType() {
        return hashType;
    }

    public void setHashType(HashType hashType) {
        this.hashType = hashType;
    }
}
