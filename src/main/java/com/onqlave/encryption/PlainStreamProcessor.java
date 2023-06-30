package com.onqlave.encryption;

import com.onqlave.types.AlgorithmSeriliser;

public interface PlainStreamProcessor {
    public void WriteHeader(AlgorithmSeriliser algorithm) throws Exception;

    public void WritePacket(byte[] packet) throws Exception;
}



