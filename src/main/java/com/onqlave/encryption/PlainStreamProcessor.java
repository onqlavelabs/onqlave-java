package com.onqlave.encryption;

import com.onqlave.types.AlgorithmDeserialiser;

public interface PlainStreamProcessor {
    public void WriteHeader(AlgorithmDeserialiser algorithm) throws Exception;
    public void WritePacket(byte[] packet)throws Exception;
}



