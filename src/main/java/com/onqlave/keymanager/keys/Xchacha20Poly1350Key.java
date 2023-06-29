package com.onqlave.keymanager.keys;

import com.onqlave.keymanager.factories.Xchacha20Poly1305Factory;
import com.onqlave.types.Key;
import com.onqlave.types.KeyData;
import com.onqlave.types.KeyOperation;

public class Xchacha20Poly1350Key implements Key {
    public KeyOperation operation;
    public Xchacha20Poly1350KeyData data;
    public int keyID;

    public Xchacha20Poly1350Key(KeyOperation operation, Xchacha20Poly1350KeyData data, int keyID) {
        this.operation = operation;
        this.data = data;
        this.keyID = keyID;
    }

    @Override
    public int KeyID() {
        return 0;
    }

    @Override
    public KeyOperation Operation() {
        return this.operation;
    }

    @Override
    public KeyData Data() {
        return this.data;
    }
}
