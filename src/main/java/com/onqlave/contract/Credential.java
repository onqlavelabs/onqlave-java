package com.onqlave.contract;

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

    public String getSigningKey() { return signingKey; }

    public String getSecretKey() {
        return secretKey;
    }
}
