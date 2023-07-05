package com.onqlave.types;

public interface KeyData {
    byte[] GetValue();
    void FromValue(byte[] data) throws  Exception;
    String GetTypeURL();
    KeyMaterialType GetKeyMaterialType();
    int GetVersion();
}

