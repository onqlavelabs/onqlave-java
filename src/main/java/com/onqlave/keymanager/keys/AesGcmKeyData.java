package com.onqlave.keymanager.keys;

import com.onqlave.types.KeyData;
import com.onqlave.types.KeyMaterialType;

public class AesGcmKeyData implements KeyData {
    private String typeURL;
    private final byte[] value;
    private final KeyMaterialType keyMaterialType;
    private final int version;

    public AesGcmKeyData(byte[] value, KeyMaterialType keyMaterialType, int version) {
        this.value = value;
        this.keyMaterialType = keyMaterialType;
        this.version = version;
    }

    @Override
    public byte[] getValue() {
        return this.value;
    }

    @Override
    public void fromValue(byte[] data) {

    }

    @Override
    public String getTypeURL() {
        return this.typeURL;
    }

    @Override
    public KeyMaterialType getKeyMaterialType() {
        return this.keyMaterialType;
    }

    @Override
    public int getVersion() {
        return this.version;
    }
}
