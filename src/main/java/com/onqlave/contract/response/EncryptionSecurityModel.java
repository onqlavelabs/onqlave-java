package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class EncryptionSecurityModel {
    @SerializedName(value = "algo")
    private String algorithm;
    @SerializedName(value = "wrapping_algo")
    private String wrappingAlgorithm;

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getWrappingAlgorithm() {
        return this.wrappingAlgorithm;
    }
}
