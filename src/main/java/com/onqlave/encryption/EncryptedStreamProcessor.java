package com.onqlave.encryption;

import com.onqlave.types.AlgorithmDeserialiser;

public interface EncryptedStreamProcessor {
    public AlgorithmDeserialiser ReadHeader() throws Exception;

    public byte[] ReadPacket() throws Exception;
}
