package com.onqlave.types;

import org.javatuples.Tuple;

public interface Unwrapping {
    byte[] UnwrapKey(byte[] wdk, byte[] epk, byte[] fp, byte[] password) throws Exception;
}
