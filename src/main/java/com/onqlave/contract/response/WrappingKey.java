package com.onqlave.contract.response;

import com.google.gson.annotations.SerializedName;

public class WrappingKey {
    @SerializedName(value = "encrypted_private_key")
    private String b64EncryptedPrivateKey;
    @SerializedName(value = "key_fingerprint")
    private String b64KeyFingerprint;

    public byte[] getEncryptedPrivateKey() {
        return this.b64EncryptedPrivateKey.getBytes();
    }

    public byte[] getKeyFingerprint() {
        return this.b64KeyFingerprint.getBytes();
    }
}



