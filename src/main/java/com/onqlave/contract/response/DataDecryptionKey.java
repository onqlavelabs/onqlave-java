package com.onqlave.contract.response;

public class DataDecryptionKey {
    private byte[] WrappedDataKey;

    public DataDecryptionKey(byte[] WrappedDataKey) {
        this.WrappedDataKey = WrappedDataKey;
    }

    public byte[] getWrappedDataKey() {
        return WrappedDataKey;
    }

    public void setWrappedDataKey(byte[] WrappedDataKey) {
        this.WrappedDataKey = WrappedDataKey;
    }
}
