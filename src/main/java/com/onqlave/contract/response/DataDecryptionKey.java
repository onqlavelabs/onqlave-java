package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class DataDecryptionKey {

    @SerializedName(value = "wrapped_data_key")
    private String WrappedDataKey;

//    public DataDecryptionKey(byte[] WrappedDataKey) {
//        this.WrappedDataKey = WrappedDataKey;
//    }

    public byte[] getWrappedDataKey() {
        return WrappedDataKey.getBytes();
    }

//    public void setWrappedDataKey(byte[] WrappedDataKey) {
//        this.WrappedDataKey = WrappedDataKey;
//    }
}
