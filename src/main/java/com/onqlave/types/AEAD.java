package com.onqlave.types;

public interface AEAD {
    public byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception;

    public byte[] Decrypt(byte[] plaintext, byte[] associatedData) throws Exception;
}
