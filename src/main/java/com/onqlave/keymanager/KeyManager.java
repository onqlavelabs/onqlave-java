package com.onqlave.keymanager;

import com.onqlave.contract.EncryptionKey;
import org.javatuples.Triplet;

public interface KeyManager {
    EncryptionKey FetchEncryptionKey() throws Exception;
    byte[] FetchDecryptionKey(byte[] edk) throws Exception;
}
