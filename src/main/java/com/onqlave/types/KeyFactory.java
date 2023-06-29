package com.onqlave.types;

public interface KeyFactory {
    Key NewKey(KeyOperation operation) throws Exception;

    Key NewKeyFromData(KeyOperation operation, byte[] keyData) throws Exception;

    AEAD Primitive(Key key) throws Exception;
}
