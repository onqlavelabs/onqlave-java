package com.onqlave.contract.response;

public class DecryptionOpenResponse extends BaseErrorResponse {
    private WrappingKey WK;
    private EncryptionSecurityModel securityModel;
    private DataDecryptionKey DK;

    public WrappingKey getWK() {
        return WK;
    }

    public void setWK(WrappingKey WK) {
        this.WK = WK;
    }

    public EncryptionSecurityModel getSecurityModel() {
        return securityModel;
    }

    public void setSecurityModel(EncryptionSecurityModel securityModel) {
        this.securityModel = securityModel;
    }

    public DataDecryptionKey getDK() {
        return DK;
    }

    public void setDK(DataDecryptionKey DK) {
        this.DK = DK;
    }
}
