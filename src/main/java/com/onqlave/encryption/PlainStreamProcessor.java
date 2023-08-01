package com.onqlave.encryption;

import com.onqlave.types.AlgorithmSeriliser;

public interface PlainStreamProcessor {
    void writeHeader(AlgorithmSeriliser algorithm) throws Exception;
    void writePacket(byte[] packet) throws Exception;
}



