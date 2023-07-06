package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.AEAD;
import com.onqlave.types.Key;
import com.onqlave.utils.XChaCha20Poly1305Helper;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import java.util.Arrays;

import static com.onqlave.utils.Constants.*;

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

    private byte[] generateNonce() {
        return randomService.GetRandomBytes(XChaCha20Poly1305NonceSize);
    }

    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
