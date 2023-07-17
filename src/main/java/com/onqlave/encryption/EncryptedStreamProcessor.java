package com.onqlave.encryption;

import com.onqlave.types.AlgorithmDeserialiser;

public interface EncryptedStreamProcessor {
    AlgorithmDeserialiser readHeader() throws Exception;

    byte[] readPacket() throws Exception;
}
