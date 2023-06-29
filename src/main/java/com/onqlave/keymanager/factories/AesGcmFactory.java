package com.onqlave.keymanager.factories;

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
    public Key NewKey(KeyOperation operation) throws Exception {
        KeyFormat format = operation.GetFormat();
        if (this.validateKeyFormat(format)) {
            throw new Exception("aesGcmKeyFactory: invalid key format.");
        }

        byte[] keyData = this.randomService.GetRandomBytes(format.Size());
        return new AesGcmKey(
                this.idService.NewKeyID(), operation, new AesGcmKeyData(
                keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public Key NewKeyFromData(KeyOperation operation, byte[] keyData) throws Exception {
        KeyFormat format = operation.GetFormat();
        if (this.validateKeyFormat(format)) {
            throw new Exception("aesGcmKeyFactory: invalid key format.");
        }

        return new AesGcmKey(this.idService.NewKeyID(), operation,
                new AesGcmKeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public AEAD Primitive(Key key) throws Exception {
        if (this.validateKey(key)) {
            throw new Exception("aesGcmKeyFactory: invalid key.");
        }
        return new AesGcmAead(key, this.randomService);
    }

    private boolean validateKey(Key key) throws Exception {
        try {
            KeyData keyData = key.Data();
            if (this.validateKeyVersion(keyData.GetVersion(), Aes128GcmOperation.AES128_GCM_KEY_VERSION)) {
                return false;
            }

            if (AesGcmAead.validateKeySize(keyData.GetValue().length)) {
                return false;
            }
        } catch (Exception e) {
            throw new Exception("aesGcmKeyFactory: invalid key version: %s");
        }

        return true;
    }

    private boolean validateKeyFormat(KeyFormat format) throws Exception {
        try {
            if (AesGcmAead.validateKeySize(format.Size())) {
                return true;
            }
        } catch (Exception e) {
            throw new Exception("aesGcmKeyFactory: invalid key format");
        }

        return false;
    }

    private boolean validateKeyVersion(int version, int maxExpected) throws Exception {
        if (version > maxExpected) {
            throw new Exception(String.format("key has version %d; only keys with version in range [0..%d] are supported", version, maxExpected));
        }
        return true;
    }
}
