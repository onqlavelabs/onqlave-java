package com.onqlave.keymanager.keys;

import com.onqlave.types.Key;
import com.onqlave.types.KeyData;
import com.onqlave.types.KeyOperation;

public class AesGcmKey implements Key {
    public KeyOperation operation;
    public AesGcmKeyData data;
    public int keyID;

    public AesGcmKey(int keyID, KeyOperation operation, AesGcmKeyData data) {
        this.keyID = keyID;
        this.operation = operation;
        this.data = data;
    }

    @Override
    public int KeyID() {
        return this.keyID;
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
