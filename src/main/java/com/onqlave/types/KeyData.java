package com.onqlave.types;

public interface KeyData {
    byte[] GetValue() throws Exception;
    void FromValue(byte[] data) throws  Exception;
    String GetTypeURL();
    int GetKeyMaterialType();
    int GetVersion();
}

