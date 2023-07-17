package com.onqlave.keymanager.factories;

import com.onqlave.exception.SecurityException;
import com.onqlave.keymanager.operations.Aes128GcmOperation;
import com.onqlave.service.CPRNGService;
import com.onqlave.service.IDService;
import com.onqlave.types.*;
import com.onqlave.keymanager.keys.*;
import com.onqlave.keymanager.primitives.*;

public class AesGcmFactory implements KeyFactory {
    public IDService idService;
    public CPRNGService randomService;

    public AesGcmFactory(IDService idService, CPRNGService randomService) {
        this.idService = idService;
        this.randomService = randomService;
    }

    @Override
    public Key newKey(KeyOperation operation) throws Exception {
        KeyFormat format = operation.getFormat();
        if (this.validateKeyFormat(format)) {
            throw new SecurityException("aesGcmKeyFactory: invalid key format.");
        }

        byte[] keyData = this.randomService.getRandomBytes(format.size());
        return new AesGcmKey(
                this.idService.newKeyID(), operation, new AesGcmKeyData(
                keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public Key newKeyFromData(KeyOperation operation, byte[] keyData) throws Exception {
        KeyFormat format = operation.getFormat();
        if (!this.validateKeyFormat(format)) {
            throw new Exception("aesGcmKeyFactory: invalid key format.");
        }

        return new AesGcmKey(this.idService.newKeyID(), operation,
                new AesGcmKeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public AEAD primitive(Key key) throws Exception {
        if (!this.validateKey(key)) {
            throw new Exception("aesGcmKeyFactory: invalid key.");
        }
        return new AesGcmAead(key, this.randomService);
    }

    private boolean validateKey(Key key) {
        KeyData keyData = key.data();
        if (!this.validateKeyVersion(keyData.getVersion(), Aes128GcmOperation.AES128_GCM_KEY_VERSION)) {
            return false;
        }

        if (!AesGcmAead.validateKeySize(keyData.getValue().length)) {
            return false;
        }

        return true;
    }

    private boolean validateKeyFormat(KeyFormat format) {
        if (!AesGcmAead.validateKeySize(format.size())) {
            return false;
        }
        return true;
    }

    private boolean validateKeyVersion(int version, int maxExpected) {
        if (version > maxExpected) {
            return false;
        }
        return true;
    }
}
