package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;

public class Xchacha20Poly1305Aead implements AEAD {
    private CPRNGService randomService;
    private Key key;

    public Xchacha20Poly1305Aead(CPRNGService randomService, Key key) {
        this.randomService = randomService;
        this.key = key;
    }

    @Override
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] Decrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return new byte[0];
    }
}
