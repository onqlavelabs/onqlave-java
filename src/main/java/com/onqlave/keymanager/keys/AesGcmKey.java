package com.onqlave.keymanager.keys;

import com.onqlave.types.Key;
import com.onqlave.types.KeyData;
import com.onqlave.types.KeyOperation;

public class AesGcmKey implements Key {
    public KeyOperation operation;
    public AesGcmKeyData data;
    public int keyID;

    public AesGcmKey(KeyOperation operation, AesGcmKeyData data, int keyID) {
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
        return null;
    }

    @Override
    public KeyData Data() {
        return null;
    }
}
