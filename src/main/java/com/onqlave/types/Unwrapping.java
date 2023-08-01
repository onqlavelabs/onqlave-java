package com.onqlave.types;

public interface Unwrapping {
    byte[] unwrapKey(byte[] wdk, byte[] epk, byte[] fp, byte[] password) throws Exception;
}
