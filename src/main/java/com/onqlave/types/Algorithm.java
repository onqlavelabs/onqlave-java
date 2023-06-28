package com.onqlave.types;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Algorithm implements AlgorithmSeriliser, AlgorithmDeserialiser {
    private byte version;
    private byte algo;
    private byte[] key;

    public Algorithm(byte version, byte algo, byte[] key) {
        this.version = version;
        this.algo = algo;
        this.key = key;
    }

    public Algorithm() {

    }
    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getAlgo() {
        return this.algo;
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


    public  AlgorithmSeriliser NewAlgorithmSerialiser(byte version, String algo, byte[] key) {
        byte algoValue = (byte) AlgorithmTypeValue.fromValue(algo);
        return new Algorithm(version, algoValue, key);
    }

    public static AlgorithmDeserialiser NewAlgorithmDeserialiser() {
        return new Algorithm();
    }


    @Override
    public byte[] Key() {
        return key;
    }

    @Override
    public byte Version() {
        return version;
    }

    @Override
    public String Algorithm() {
        return AlgorithmTypeName.fromValue(algo);
    }

    @Override
    public byte[] Serialise() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(7 + key.length);
        buffer.put(version);
        buffer.put(algo);
        buffer.put((byte) key.length);
        buffer.put(key);
        buffer.flip();
        byte[] serialised = new byte[buffer.remaining()];
        buffer.get(serialised);
        return serialised;
    }

    @Override
    public int Deserialise(byte[] buffer) throws Exception {
        if (buffer.length < 7) {
            throw new Exception("[Deserialise] Invalid Cipher Data");
        }
        int headerLen = ByteBuffer.wrap(buffer, 0, 4).order(ByteOrder.BIG_ENDIAN).getInt();
        if (buffer.length < headerLen) {
            throw new Exception("[Deserialise] Invalid Cipher Data");
        }
        version = buffer[4];
        algo = buffer[5];
        byte keyLen = buffer[6];
        key = Arrays.copyOfRange(buffer, 7, 7 + keyLen);
        return headerLen;
    }
}

