package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;
import com.onqlave.utils.XChaCha20Poly1305Helper;

public class Xchacha20Poly1305Aead implements AEAD {
    private CPRNGService randomService;
    private Key key;
    private boolean prependIV;

    public Xchacha20Poly1305Aead(CPRNGService randomService, Key key, boolean prependIV) {
        this.randomService = randomService;
        this.key = key;
        this.prependIV = prependIV;
    }

    @Override
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception {
        return XChaCha20Poly1305Helper.Encrypt(key.Data().GetValue(), plaintext, associatedData);
    }

    @Override
    public byte[] Decrypt(byte[] cipherText, byte[] associatedData) throws Exception {
        return XChaCha20Poly1305Helper.Decrypt(this.key.Data().GetValue(), cipherText, associatedData);
    }
}
