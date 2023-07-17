package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;
import com.onqlave.utils.XChaCha20Poly1305Helper;

public class Xchacha20Poly1305Aead implements AEAD {
    private final CPRNGService randomService;
    private final Key key;
    private final boolean prependIV;

    public Xchacha20Poly1305Aead(CPRNGService randomService, Key key, boolean prependIV) {
        this.randomService = randomService;
        this.key = key;
        this.prependIV = prependIV;
    }

    @Override
    public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return XChaCha20Poly1305Helper.encrypt(key.data().getValue(), plaintext, associatedData);
    }

    @Override
    public byte[] decrypt(byte[] cipherText, byte[] associatedData) throws Exception {
        return XChaCha20Poly1305Helper.decrypt(this.key.data().getValue(), cipherText, associatedData);
    }
}
