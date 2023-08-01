package com.onqlave.contract.response;


import com.google.gson.annotations.SerializedName;

public class EncryptionOpenResponse extends BaseErrorResponse {
    @SerializedName(value = "wrapping_key")
    private WrappingKey WK;
    @SerializedName(value = "data_key")
    private DataEncryptionKey DK;
    @SerializedName(value = "security_model")
    private EncryptionSecurityModel securityModel;
    @SerializedName(value = "max_uses")
    private int maxUses;

    public WrappingKey getWK() {
        return WK;
    }

    public void setWK(WrappingKey WK) {
        this.WK = WK;
    }

    public DataEncryptionKey getDK() {
        return DK;
    }

    public EncryptionSecurityModel getSecurityModel() {
        return this.securityModel;
    }
}

