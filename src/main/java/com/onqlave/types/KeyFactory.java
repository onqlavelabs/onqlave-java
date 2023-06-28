package com.onqlave.types;

public interface KeyFactory {
    public Key NewKey(KeyOperation operation) throws Exception;

    public Key NewKeyFromData(KeyOperation operation, byte[] keyData) throws Exception;

    public AEAD Primitive(Key key) throws Exception;
}
