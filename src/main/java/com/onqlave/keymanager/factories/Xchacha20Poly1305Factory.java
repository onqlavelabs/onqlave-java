package com.onqlave.keymanager.factories;

import com.onqlave.service.CPRNGService;
import com.onqlave.service.IDService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;
import com.onqlave.types.KeyFactory;
import com.onqlave.types.KeyOperation;

public class Xchacha20Poly1305Factory implements KeyFactory {
    public IDService idService;
    public CPRNGService randomService;

    public Xchacha20Poly1305Factory(IDService idService, CPRNGService randomService) {
        this.idService = idService;
        this.randomService = randomService;
    }

    @Override
    public Key NewKey(KeyOperation operation) throws Exception {
        return null;
    }

    @Override
    public Key NewKeyFromData(KeyOperation operation, byte[] keyData) throws Exception {
        return null;
    }

    @Override
    public AEAD Primitive(Key key) throws Exception {
        return null;
    }
}
