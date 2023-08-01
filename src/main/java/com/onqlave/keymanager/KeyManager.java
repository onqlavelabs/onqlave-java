package com.onqlave.keymanager;

import com.onqlave.contract.EncryptionKey;

public interface KeyManager {
    EncryptionKey fetchEncryptionKey() throws Exception;

    byte[] fetchDecryptionKey(byte[] edk) throws Exception;
}
