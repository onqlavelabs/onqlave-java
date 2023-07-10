package com.onqlave.keymanager.factories;

import com.onqlave.keymanager.keys.Xchacha20Poly1350Key;
import com.onqlave.keymanager.keys.Xchacha20Poly1350KeyData;
import com.onqlave.keymanager.operations.Xchacha20Poly1305Operation;
import com.onqlave.keymanager.primitives.Xchacha20Poly1305Aead;
import com.onqlave.service.CPRNGService;
import com.onqlave.service.IDService;
import com.onqlave.types.*;

import static com.onqlave.utils.Constants.XChaCha20Poly1305KeySize;

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
        ValidateKeyFormat(format);
        byte[] keyData = this.randomService.GetRandomBytes(format.Size());
        return new Xchacha20Poly1350Key(this.idService.NewKeyID(), operation, new Xchacha20Poly1350KeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public Key NewKeyFromData(KeyOperation operation, byte[] keyData) throws Exception {
        KeyFormat format = operation.GetFormat();
        ValidateKeyFormat(format);
        return new Xchacha20Poly1350Key(this.idService.NewKeyID(), operation, new Xchacha20Poly1350KeyData(keyData, KeyMaterialType.KeyMaterialSYMMETRIC, 0));
    }

    @Override
    public AEAD Primitive(Key key) throws Exception {
        ValidateKey(key);
        return new Xchacha20Poly1305Aead(this.randomService, key, true);
    }

    private static void ValidateKey(Key key) throws Exception {
        ValidateKeyVersion(key.Data().GetVersion(), Xchacha20Poly1305Operation.XCHACHA20_POLY1305_VERSION);
        byte[] value = key.Data().GetValue();
        ValidateXChaChaKeySize(value.length);
    }

    private static void ValidateKeyFormat(KeyFormat format) throws Exception {
        ValidateXChaChaKeySize(format.Size());
    }


    private static void ValidateKeyVersion(int version, int maxExpected) throws Exception {
        if (version > maxExpected) {
            throw new Exception(String.format("key has version %d; only keys with version in range [0..%d] are supported", version, maxExpected));
        }
    }

    private static void ValidateXChaChaKeySize(int sizeInBytes) throws Exception {
        if (sizeInBytes != XChaCha20Poly1305KeySize) {
            throw new Exception(String.format("invalid XChaCha key size; want %d , got %d", XChaCha20Poly1305KeySize, sizeInBytes));
        }
    }
}
