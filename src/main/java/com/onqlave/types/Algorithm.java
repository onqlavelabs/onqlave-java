package com.onqlave.types;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Algorithm implements AlgorithmSeriliser, AlgorithmDeserialiser {
    private byte version;
    private byte algo;
    private byte[] key;

    public Algorithm(int version, String algo, byte[] key) {
        this.version = (byte) version;
        this.algo = (byte) AlgorithmTypeValue.fromValue(algo);
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

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    @Override
    public byte[] key() {
        return key;
    }

    @Override
    public byte version() {
        return version;
    }

    @Override
    public String getAlgorithm() {
        return AlgorithmTypeName.fromValue(algo);
    }

    @Override
    public byte[] serialise() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteBuffer headerLenBuffer = ByteBuffer.allocate(4);
        headerLenBuffer.order(ByteOrder.BIG_ENDIAN);
        headerLenBuffer.putInt(7 + key.length);
        outputStream.write(headerLenBuffer.array());
        outputStream.write(version);
        outputStream.write(algo);
        outputStream.write(key.length);
        outputStream.writeBytes(key);
        return outputStream.toByteArray();
    }

    @Override
    public int deserialise(byte[] buffer) throws Exception {
        if (buffer.length < 7) {
            throw new IllegalArgumentException("Invalid cipher data");
        }

        int headerLen = ByteBuffer.wrap(buffer, 0, 4).order(ByteOrder.BIG_ENDIAN).getInt();
        if (buffer.length < headerLen) {
            throw new IllegalArgumentException("Invalid cipher data");
        }

        version = buffer[4];
        algo = buffer[5];
        int keyLen = buffer[6] & 0xFF;

        key = new byte[keyLen];
        System.arraycopy(buffer, 7, key, 0, keyLen);
        return keyLen;
    }
}