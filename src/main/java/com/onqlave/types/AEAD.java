package com.onqlave.types;

public interface AEAD {
    byte[] Encrypt(byte[] plaintext, byte[] associatedData) throws Exception;

    byte[] Decrypt(byte[] plaintext, byte[] associatedData) throws Exception;
}
