package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class DataEncryptionKey {
    @SerializedName(value = "encrypted_data_key")
    private String b64EncryptedDataKey;
    @SerializedName(value = "wrapped_data_key")
    private String b64WrappedDataKey;

    public byte[] getEncryptedDataKey() {
        return this.b64EncryptedDataKey.getBytes();
    }

    public byte[] getWrappedDataKey() {
        return this.b64WrappedDataKey.getBytes();
    }

}

