package com.onqlave.encryption;

import com.onqlave.types.AlgorithmDeserialiser;

import java.io.Writer;

public class PlainStreamProcessorImpl implements PlainStreamProcessor {
    public Writer getCipherStream() {
        return cipherStream;
    }

    public void setCipherStream(Writer cipherStream) {
        this.cipherStream = cipherStream;
    }

    private  Writer cipherStream;

    public PlainStreamProcessorImpl(Writer cipherStream) {
        this.cipherStream = cipherStream;
    }

    @Override
    public void WriteHeader(AlgorithmDeserialiser algorithm) throws Exception {

    }

    @Override
    public void WritePacket(byte[] packet) throws Exception {

    }
}
