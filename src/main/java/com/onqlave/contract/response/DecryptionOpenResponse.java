package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class DecryptionOpenResponse extends BaseErrorResponse {
    @SerializedName(value = "wrapping_key")
    private WrappingKey WK;

    @SerializedName(value = "security_model")
    private EncryptionSecurityModel securityModel;

    @SerializedName(value = "data_key")
    private DataDecryptionKey DK;

    public WrappingKey getWK() {
        return WK;
    }

    public EncryptionSecurityModel getSecurityModel() {
        return securityModel;
    }

    public DataDecryptionKey getDK() {
        return DK;
    }
}
