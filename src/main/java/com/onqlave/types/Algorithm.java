package com.onqlave.types;

public class Algorithm {
    private byte version;
    private byte algo;
    private byte[] key;

    public Algorithm(byte version, byte algo, byte[] key) {
        this.version = version;
        this.algo = algo;
        this.key = key;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getAlgo() {
        return algo;
    }

    public void setAlgo(byte algo) {
        this.algo = algo;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public AlgorithmSeriliser NewAlgorithmSerialiser(byte version, String algo, byte[] key) {
//        byte algoValue = (byte)AlgorithmTypeValue.fromValue(algo);
//        return  new Algorithm(version, algoValue, key);
        return null;
    }

    public static AlgorithmDeserialiser NewAlgorithmDeserialiser() {
        return null;
    }


}
