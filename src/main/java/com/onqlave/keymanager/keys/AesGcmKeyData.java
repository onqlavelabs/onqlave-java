package com.onqlave.keymanager.keys;

import com.onqlave.types.KeyData;
import com.onqlave.types.KeyMaterialType;

public class AesGcmKeyData implements KeyData {
    public String typeURL;
    public byte[] value;
    public KeyMaterialType keyMaterialType;
    public int version;

    public AesGcmKeyData(String typeURL, byte[] value, KeyMaterialType keyMaterialType, int version) {
        this.typeURL = typeURL;
        this.value = value;
        this.keyMaterialType = keyMaterialType;
        this.version = version;
    }

    public AesGcmKeyData(byte[] value, KeyMaterialType keyMaterialType, int version) {
        this.value = value;
        this.keyMaterialType = keyMaterialType;
        this.version = version;
    }

    @Override
    public byte[] GetValue() {
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
