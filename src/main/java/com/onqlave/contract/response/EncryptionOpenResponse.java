package com.onqlave.contract.response;


public class EncryptionOpenResponse extends BaseErrorResponse {
    private WrappingKey WK;
    private DataEncryptionKey DK;
    private EncryptionSecurityModel SecurityModel;
    private int MaxUses;

    public EncryptionOpenResponse(WrappingKey WK, DataEncryptionKey DK, EncryptionSecurityModel SecurityModel, int MaxUses) {
        this.WK = WK;
        this.DK = DK;
        this.SecurityModel = SecurityModel;
        this.MaxUses = MaxUses;
    }

    public WrappingKey getWK() {
        return WK;
    }

    public void setWK(WrappingKey WK) {
        this.WK = WK;
    }

    public DataEncryptionKey getDK() {
        return DK;
    }

    public void setDK(DataEncryptionKey DK) {
        this.DK = DK;
    }

    public EncryptionSecurityModel getSecurityModel() {
        return SecurityModel;
    }

    public void setSecurityModel(EncryptionSecurityModel SecurityModel) {
        this.SecurityModel = SecurityModel;
    }

    public int getMaxUses() {
        return MaxUses;
    }

    public void setMaxUses(int MaxUses) {
        this.MaxUses = MaxUses;
    }
}

