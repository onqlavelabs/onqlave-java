package com.onqlave.keymanager;

import org.javatuples.Triplet;

public interface KeyManager {
    Triplet<byte[], byte[], String> FetchEncryptionKey() throws Exception;
    byte[] FetchDecryptionKey(byte[] edk) throws Exception;
}
