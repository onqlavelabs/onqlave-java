package com.onqlave.types;

public interface AEAD {
    byte[] encrypt(byte[] plaintext, byte[] associatedData) throws Exception;
    byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws Exception;
}
