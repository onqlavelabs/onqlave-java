package com.onqlave.keymanager;

import com.onqlave.contract.EncryptionKey;
import org.javatuples.Triplet;

public interface KeyManager {
    EncryptionKey fetchEncryptionKey() throws Exception;
    byte[] fetchDecryptionKey(byte[] edk) throws Exception;
}
