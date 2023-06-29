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
    public byte[] GetValue() throws Exception {
        return new byte[0];
    }

    @Override
    public void FromValue(byte[] data) throws Exception {

    }

    @Override
    public String GetTypeURL() {
        return null;
    }

    @Override
    public int GetKeyMaterialType() {
        return 0;
    }

    @Override
    public int GetVersion() {
        return this.keyMaterialType.getValue();
    }
}
