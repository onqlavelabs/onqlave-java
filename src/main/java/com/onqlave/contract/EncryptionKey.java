package com.onqlave.contract;

public class EncryptionKey {
    private byte[] b64EDK;
    private byte[] b4DK;
    private String algorithm;

    public EncryptionKey(byte[] edk, byte[] dk, String algorithm) {
        this.b64EDK = edk;
        this.b4DK = dk;
        this.algorithm = algorithm;
    }

    public EncryptionKey() {

    }

    public byte[] getB64EDK() {
        return b64EDK;
    }

    public void setB64EDK(byte[] b64EDK) {
        this.b64EDK = b64EDK;
    }

    public byte[] getB4DK() {
        return b4DK;
    }

    public void setB4DK(byte[] b4DK) {
        this.b4DK = b4DK;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
