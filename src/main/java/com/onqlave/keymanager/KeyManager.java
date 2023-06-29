package com.onqlave.keymanager;

import org.javatuples.Tuple;

public interface KeyManager {
    Tuple FetchEncryptionKey() throws Exception;

    Tuple FetchDecryptionKey() throws Exception;
}
