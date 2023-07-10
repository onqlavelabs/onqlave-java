package com.onqlave.keymanager.factories;

import com.onqlave.exception.SecurityException;
import com.onqlave.keymanager.operations.Aes128GcmOperation;
import com.onqlave.service.CPRNGService;
import com.onqlave.service.IDService;
import com.onqlave.types.*;
import com.onqlave.keymanager.keys.*;
import com.onqlave.keymanager.primitives.*;

import static com.onqlave.keymanager.primitives.AesGcmAead.ValidateKeySize;

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
        ValidateKeyFormat(format);
        byte[] keyData = this.randomService.GetRandomBytes(format.Size());
        return new AesGcmKey(this.idService.NewKeyID(), operation, new AesGcmKeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public Key NewKeyFromData(KeyOperation operation, byte[] keyData) throws Exception {
        KeyFormat format = operation.GetFormat();
        ValidateKeyFormat(format);
        return new AesGcmKey(this.idService.NewKeyID(), operation, new AesGcmKeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public AEAD Primitive(Key key) throws Exception {
        ValidateKey(key);
        return new AesGcmAead(key, this.randomService);
    }

    private static void ValidateKey(Key key) throws Exception {
        KeyData keyData = key.Data();
        ValidateKeyVersion(keyData.GetVersion(), Aes128GcmOperation.AES128_GCM_KEY_VERSION);
        ValidateKeySize(keyData.GetValue().length);
    }

    private static void ValidateKeyFormat(KeyFormat format) throws Exception {
        ValidateKeySize(format.Size());
    }


    private static void ValidateKeyVersion(int version, int maxExpected) throws Exception {
        if (version > maxExpected) {
            throw new Exception(String.format("key has version %d; only keys with version in range [0..%d] are supported", version, maxExpected));
        }
    }
}
