package com.onqlave.contract.config;

public class Credential {
    private String accessKey;
    private String signingKey;
    private String secretKey;

    public Credential(String accessKey, String signingKey, String secretKey) {
        this.accessKey = accessKey;
        this.signingKey = signingKey;
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
