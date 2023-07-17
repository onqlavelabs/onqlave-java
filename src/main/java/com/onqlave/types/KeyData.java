package com.onqlave.types;

public interface KeyData {
    byte[] getValue();
    void fromValue(byte[] data) throws  Exception;
    String getTypeURL();
    KeyMaterialType getKeyMaterialType();
    int getVersion();
}

