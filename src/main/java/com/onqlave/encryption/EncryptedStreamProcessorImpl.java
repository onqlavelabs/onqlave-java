package com.onqlave.encryption;

import com.onqlave.types.Algorithm;
import com.onqlave.types.AlgorithmDeserialiser;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class EncryptedStreamProcessorImpl implements EncryptedStreamProcessor {
    private InputStream cipherStream;

    public EncryptedStreamProcessorImpl(InputStream cipherStream) {
        this.cipherStream = cipherStream;
    }

    @Override
    public AlgorithmDeserialiser readHeader() throws Exception {
        byte[] headerLenBuffer = new byte[4];
        int dataLen = this.cipherStream.read(headerLenBuffer);
        if (dataLen == -1) {
            throw new IOException("End of stream reached");
        }

        if (dataLen < 4) {
            throw new IOException("Invalid cipher data");
        }
        int headerLen = ByteBuffer.wrap(headerLenBuffer).getInt();
        byte[] headerBuffer = new byte[headerLen - 4];
        dataLen = this.cipherStream.read(headerBuffer);
        if (dataLen == -1) {
            throw new IOException("End of stream reached");
        }
        if (dataLen < headerLen - 4) {
            throw new IOException("Invalid cipher data");
        }
        AlgorithmDeserialiser algorithm = new Algorithm();
        algorithm.deserialise(concatArrays(headerLenBuffer, headerBuffer));
        return algorithm;
    }

    @Override
    public byte[] readPacket() throws Exception {
        byte[] packetLenBuffer = new byte[4];
        int dataLen = cipherStream.read(packetLenBuffer);
        if (dataLen < 4) {
            throw new IOException("Invalid cipher data");
        }
        int packetLen = ByteBuffer.wrap(packetLenBuffer).getInt();
        byte[] buffer = new byte[packetLen];
        dataLen = cipherStream.read(buffer);
        if (dataLen < packetLen) {
            throw new IOException("Invalid cipher data");
        }
        return buffer;
    }

    private byte[] concatArrays(byte[] arr1, byte[] arr2) {
        byte[] result = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}