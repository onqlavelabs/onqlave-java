package com.onqlave.encryption;

import com.onqlave.types.AlgorithmDeserialiser;

import java.io.Reader;

public class EncryptedStreamProcessorImpl implements EncryptedStreamProcessor {
    public Reader getCipherStream() {
        return cipherStream;
    }

    public void setCipherStream(Reader cipherStream) {
        this.cipherStream = cipherStream;
    }

    private  Reader cipherStream;

    public EncryptedStreamProcessorImpl(Reader cipherStream) {
        this.cipherStream = cipherStream;
    }

    @Override
    public AlgorithmDeserialiser ReadHeader()throws Exception{
        // Implementation goes here
        return null;
    }

    @Override
    public byte[] ReadPacket() throws Exception {
        // Implementation goes here
        return null;
    }
}