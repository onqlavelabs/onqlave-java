package com.onqlave.contract.response;

public class DataEncryptionKey {
    private byte[] EncryptedDataKey;
    private byte[] WrappedDataKey;

    public DataEncryptionKey(byte[] EncryptedDataKey, byte[] WrappedDataKey) {
        this.EncryptedDataKey = EncryptedDataKey;
        this.WrappedDataKey = WrappedDataKey;
    }

    public byte[] getEncryptedDataKey() {
        return EncryptedDataKey;
    }

    public void setEncryptedDataKey(byte[] EncryptedDataKey) {
        this.EncryptedDataKey = EncryptedDataKey;
    }

    public byte[] getWrappedDataKey() {
        return WrappedDataKey;
    }

    public void setWrappedDataKey(byte[] WrappedDataKey) {
        this.WrappedDataKey = WrappedDataKey;
    }
}

