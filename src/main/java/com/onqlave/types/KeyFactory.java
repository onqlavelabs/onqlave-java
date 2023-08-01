package com.onqlave.types;

public interface KeyFactory {
    Key newKey(KeyOperation operation) throws Exception;
    Key newKeyFromData(KeyOperation operation, byte[] keyData) throws Exception;
    AEAD primitive(Key key) throws Exception;
}
