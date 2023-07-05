package com.onqlave.types;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

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

    @Override
    public byte[] Key() {
        return key;
    }

    @Override
    public byte Version() {
        return version;
    }

    @Override
    public String GetAlgorithm() {
        return AlgorithmTypeName.fromValue(algo);
    }

    //TODO: consider to review
    @Override
    public byte[] Serialise() throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        ByteBuffer headerLenBuf = ByteBuffer.allocate(4);
        headerLenBuf.order(ByteOrder.BIG_ENDIAN);
        int headerLen = 7 + this.key.length;
        headerLenBuf.putInt(headerLen);
        byte[] headerLenBytes = headerLenBuf.array();
        buf.writeBytes(headerLenBytes);

        buf.write(this.version);
        buf.write(this.algo);
        buf.write((byte) this.key.length);
        buf.write(this.key);

        return buf.toByteArray();
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

