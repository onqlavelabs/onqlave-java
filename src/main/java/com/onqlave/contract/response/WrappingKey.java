package com.onqlave.contract.response;

public class WrappingKey {
    private byte[] EncryptedPrivateKey;
    private byte[] KeyFingerprint;

    public WrappingKey(byte[] EncryptedPrivateKey, byte[] KeyFingerprint) {
        this.EncryptedPrivateKey = EncryptedPrivateKey;
        this.KeyFingerprint = KeyFingerprint;
    }

    public byte[] getEncryptedPrivateKey() {
        return EncryptedPrivateKey;
    }

    public void setEncryptedPrivateKey(byte[] EncryptedPrivateKey) {
        this.EncryptedPrivateKey = EncryptedPrivateKey;
    }

    public byte[] getKeyFingerprint() {
        return KeyFingerprint;
    }

    public void setKeyFingerprint(byte[] KeyFingerprint) {
        this.KeyFingerprint = KeyFingerprint;
    }
}



