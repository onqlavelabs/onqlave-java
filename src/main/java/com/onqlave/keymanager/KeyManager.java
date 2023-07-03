package com.onqlave.keymanager;

import org.javatuples.Tuple;

public interface KeyManager {
    Tuple FetchEncryptionKey() throws Exception;

    byte[] FetchDecryptionKey(byte[] edk) throws Exception;
}
