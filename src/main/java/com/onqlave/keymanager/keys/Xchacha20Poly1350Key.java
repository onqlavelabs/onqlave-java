package com.onqlave.keymanager.keys;

import com.onqlave.types.Key;
import com.onqlave.types.KeyData;
import com.onqlave.types.KeyOperation;

public class Xchacha20Poly1350Key implements Key {
    private KeyOperation operation;

    private Xchacha20Poly1350KeyData data;
    private int keyID;

    public Xchacha20Poly1350Key(int keyID, KeyOperation operation, Xchacha20Poly1350KeyData data) {
        this.operation = operation;
        this.data = data;
        this.keyID = keyID;
    }

    @Override
    public int keyID() {
        return 0;
    }

    @Override
    public KeyOperation operation() {
        return this.operation;
    }

    @Override
    public KeyData data() {
        return this.data;
    }
}
