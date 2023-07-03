package com.onqlave.contract.response;

public class EncryptionSecurityModel {
    private String Algorithm;
    private String WrappingAlgorithm;

    public EncryptionSecurityModel(String Algorithm, String WrappingAlgorithm) {
        this.Algorithm = Algorithm;
        this.WrappingAlgorithm = WrappingAlgorithm;
    }

    public String getAlgorithm() {
        return Algorithm;
    }

    public void setAlgorithm(String Algorithm) {
        this.Algorithm = Algorithm;
    }

    public String getWrappingAlgorithm() {
        return WrappingAlgorithm;
    }

    public void setWrappingAlgorithm(String WrappingAlgorithm) {
        this.WrappingAlgorithm = WrappingAlgorithm;
    }
}
