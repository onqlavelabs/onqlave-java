package com.onqlave.encryption;

import com.onqlave.types.AlgorithmDeserialiser;

public interface EncryptedStreamProcessor {
    AlgorithmDeserialiser ReadHeader() throws Exception;

    byte[] ReadPacket() throws Exception;
}
