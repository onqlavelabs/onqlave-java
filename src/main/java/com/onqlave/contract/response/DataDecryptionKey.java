package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class DataDecryptionKey {

    @SerializedName(value = "wrapped_data_key")
    private String b64WrappedDataKey;

    public byte[] getWrappedDataKey() {
        return b64WrappedDataKey.getBytes();
    }
}
