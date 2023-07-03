package com.onqlave.keymanager.keys;

import com.onqlave.types.KeyData;
import com.onqlave.types.KeyMaterialType;

public class Xchacha20Poly1350KeyData implements KeyData {
    public String typeURL;
    public byte[] value;
    public KeyMaterialType keyMaterialType;
    public int version;

    public Xchacha20Poly1350KeyData(String typeURL, byte[] value, KeyMaterialType keyMaterialType, int version) {
        this.typeURL = typeURL;
        this.value = value;
        this.keyMaterialType = keyMaterialType;
        this.version = version;
    }

    public Xchacha20Poly1350KeyData(byte[] value, KeyMaterialType keyMaterialType, int version) {
        this.value = value;
        this.keyMaterialType = keyMaterialType;
        this.version = version;
    }

    @Override
    public byte[] GetValue() throws Exception {
        return this.value;
    }

    @Override
    public void FromValue(byte[] data) throws Exception {

    }

    @Override
    public String GetTypeURL() {
        return this.typeURL;
    }

    @Override
    public KeyMaterialType GetKeyMaterialType() {
        return this.keyMaterialType;
    }

    @Override
    public int GetVersion() {
        return this.version;
    }
}
