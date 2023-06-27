package com.onqlave.keymanager.factories;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.Unwrapping;
import com.onqlave.types.WrappingKeyFactory;
import com.onqlave.types.WrappingKeyOperation;

public class RsaSsaPkcs1ShaFactory implements WrappingKeyFactory {
    public CPRNGService randomService;

    public RsaSsaPkcs1ShaFactory(CPRNGService randomService) {
        this.randomService = randomService;
    }

    @Override
    public Unwrapping Primitive(WrappingKeyOperation operation) throws Exception {
        return null;
    }
}
