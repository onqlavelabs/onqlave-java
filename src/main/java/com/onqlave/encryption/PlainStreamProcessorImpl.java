package com.onqlave.encryption;

import com.onqlave.types.AlgorithmSeriliser;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PlainStreamProcessorImpl implements PlainStreamProcessor {
    private final OutputStream cipherStream;

    public PlainStreamProcessorImpl(OutputStream cipherStream) {
        this.cipherStream = cipherStream;
    }

    @Override
    public void writeHeader(AlgorithmSeriliser algorithm) throws Exception {
        byte[] header = algorithm.serialise();
        this.cipherStream.write(header);
    }

    @Override
    public void writePacket(byte[] packet) throws Exception {
        ByteBuffer dataLenBuffer = ByteBuffer.allocate(4);
        dataLenBuffer.order(ByteOrder.BIG_ENDIAN);
        dataLenBuffer.putInt(packet.length);
        byte[] dataLen = dataLenBuffer.array();
        cipherStream.write(dataLen);
        cipherStream.write(packet);
    }
}
