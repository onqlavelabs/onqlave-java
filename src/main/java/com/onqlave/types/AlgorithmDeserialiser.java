package com.onqlave.types;

public interface AlgorithmDeserialiser {
    public int Deserialise(byte[] buffer) throws  Exception;
    public byte[] Key();
    public byte Version();
    public String Algorithm();
}

