package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class WrappingKey {
    @SerializedName(value = "encrypted_private_key")
    private String encryptedPrivateKey;
    @SerializedName(value = "key_fingerprint")
    private String keyFingerprint;

    public byte[] getEncryptedPrivateKey() {
        return this.encryptedPrivateKey.getBytes();
    }

    public byte[] getKeyFingerprint() {
        return this.keyFingerprint.getBytes();
    }
}



