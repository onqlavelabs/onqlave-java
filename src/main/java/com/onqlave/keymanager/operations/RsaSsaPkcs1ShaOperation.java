package com.onqlave.keymanager.operations;

import com.onqlave.types.HashType;
import com.onqlave.types.KeyFormat;
import com.onqlave.types.WrappingKeyFactory;
import com.onqlave.types.WrappingKeyOperation;

public class RsaSsaPkcs1ShaOperation implements WrappingKeyOperation {
    public static final int RSA_SSA_PKCS1_SHA_VERSION = 0;
    private WrappingKeyFactory factory;
    private RsaSsaPkcs1ShaKeyFormat format;


    public RsaSsaPkcs1ShaOperation(WrappingKeyFactory factory) {
        RsaSsaPkcs1ShaKeyFormat format = new RsaSsaPkcs1ShaKeyFormat(HashType.SHA256, RSA_SSA_PKCS1_SHA_VERSION);
        this.factory = factory;
        this.format = format;
    }
    @Override
    public KeyFormat GetFormat() {
        return this.format;
    }

    @Override
    public WrappingKeyFactory GetFactory() {
        return this.factory;
    }

}
