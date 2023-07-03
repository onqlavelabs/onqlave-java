package com.onqlave.types;

public interface AlgorithmDeserialiser {
    int Deserialise(byte[] buffer) throws Exception;

    byte[] Key();

    byte Version();

    String GetAlgorithm();
}

