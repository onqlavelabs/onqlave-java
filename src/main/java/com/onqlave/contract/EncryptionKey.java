package com.onqlave.contract;

public class EncryptionKey {
    private final byte[] b64EDK;
    private final byte[] b4DK;
    private final String algorithm;

    public EncryptionKey(byte[] edk, byte[] dk, String algorithm) {
        this.b64EDK = edk;
        this.b4DK = dk;
        this.algorithm = algorithm;
    }

    public byte[] getB64EDK() {
        return b64EDK;
    }
    public byte[] getB4DK() {
        return b4DK;
    }
    public String getAlgorithm() {
        return algorithm;
    }
}
