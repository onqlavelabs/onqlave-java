package com.onqlave.keymanager;

import org.javatuples.Tuple;

public interface KeyManager {
    public Tuple FetchEncryptionKey() throws Exception;
    public Tuple FetchDecryptionKey() throws Exception;
}
