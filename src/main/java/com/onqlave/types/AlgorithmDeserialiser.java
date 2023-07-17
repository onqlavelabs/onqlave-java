package com.onqlave.types;

public interface AlgorithmDeserialiser {
    int deserialise(byte[] buffer) throws Exception;
    byte[] key();
    byte version();
    String getAlgorithm();
}

