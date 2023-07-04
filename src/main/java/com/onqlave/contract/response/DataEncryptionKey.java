package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class DataEncryptionKey {
    @SerializedName(value = "encrypted_data_key")
    private String encryptedDataKey;
    @SerializedName(value = "wrapped_data_key")
    private String wrappedDataKey;


    public byte[] getEncryptedDataKey() {
        return this.encryptedDataKey.getBytes();
    }


    public byte[] getWrappedDataKey() {
        return this.wrappedDataKey.getBytes();
    }

}

