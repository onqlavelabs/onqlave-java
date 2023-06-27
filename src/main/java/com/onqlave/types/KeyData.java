package com.onqlave.types;

public interface KeyData {
    public byte[] GetValue() throws Exception;
    public void FromValue(byte[] data) throws  Exception;
    public String GetTypeURL();
    public int GetKeyMaterialType();
    public int GetVersion();
}

