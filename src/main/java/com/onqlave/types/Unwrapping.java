package com.onqlave.types;

public interface Unwrapping {
    byte[] UnwrapKey(byte[] wdk, byte[] epk, byte[] fp, byte[] password) throws Exception;
}
