package com.onqlave.keymanager.factories;

import com.onqlave.keymanager.keys.Xchacha20Poly1350Key;
import com.onqlave.keymanager.keys.Xchacha20Poly1350KeyData;
import com.onqlave.keymanager.operations.Xchacha20Poly1305Operation;
import com.onqlave.keymanager.primitives.Xchacha20Poly1305Aead;
import com.onqlave.service.CPRNGService;
import com.onqlave.service.IDService;
import com.onqlave.types.*;

public class Xchacha20Poly1305Factory implements KeyFactory {
    public IDService idService;
    public CPRNGService randomService;

    public Xchacha20Poly1305Factory(IDService idService, CPRNGService randomService) {
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
        return new Xchacha20Poly1350Key(this.idService.NewKeyID(), operation,
                new Xchacha20Poly1350KeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public Key NewKeyFromData(KeyOperation operation, byte[] keyData) throws Exception {
        KeyFormat format = operation.GetFormat();
        if (this.validateKeyFormat(format)) {
            throw new Exception("aesGcmKeyFactory: invalid key format.");
        }
        return new Xchacha20Poly1350Key(this.idService.NewKeyID(), operation,
                new Xchacha20Poly1350KeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public AEAD Primitive(Key key) throws Exception {
        if (this.validateKey(key)) {
            return null;
        }
        return new Xchacha20Poly1305Aead(this.randomService, key);
    }

    private boolean validateKey(Key key) throws Exception {
        if (this.validateKeyVersion(key.Data().GetVersion(), Xchacha20Poly1305Operation.XCHACHA20_POLY1305_VERSION)) {
            return false;
        }

        byte[] value = key.Data().GetValue();

        if (this.validateXChaChaKeySize(value.length)) {
            return false;
        }

        return true;
    }

    private boolean validateKeyFormat(KeyFormat format) {
        if (this.validateXChaChaKeySize(format.Size())) {
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

    private boolean validateXChaChaKeySize(int sizeInBytes) {
        if (sizeInBytes != 32) {
            return false;
        }
        return true;
    }
}
