package com.onqlave.keymanager.factories;

import com.onqlave.keymanager.operations.RsaSsaPkcs1ShaKeyFormat;
import com.onqlave.keymanager.primitives.HashFunction;
import com.onqlave.keymanager.primitives.RsaSsaPkcs1Sha;
import com.onqlave.service.CPRNGService;
import com.onqlave.types.HashType;
import com.onqlave.types.Unwrapping;
import com.onqlave.types.WrappingKeyFactory;
import com.onqlave.types.WrappingKeyOperation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.onqlave.keymanager.primitives.HashFunction.GetAlgo;
import static com.onqlave.keymanager.primitives.HashFunction.GetHashFunc;

public class RsaSsaPkcs1ShaFactory implements WrappingKeyFactory {
    public CPRNGService randomService;

    public RsaSsaPkcs1ShaFactory(CPRNGService randomService) {
        this.randomService = randomService;
    }


    public static Object[] RSAHashFunc(String hashAlg) throws Exception {
        if (HashSafeForSignature(hashAlg) != null) {
            return new Object[]{null, 0, HashSafeForSignature(hashAlg)};
        }
        MessageDigest hashFunc = GetHashFunc(hashAlg);
        if (hashFunc == null) {
            return new Object[]{null, 0, new Exception(String.format("invalid hash function: %s", hashAlg))};
        }
        int hashID = hashFunc.hashCode();
        return new Object[]{hashFunc, hashID, null};
    }

    public static Exception HashSafeForSignature(String hashAlg) {
        return switch (hashAlg) {
            case "SHA256", "SHA384", "SHA512" -> null;
            default ->
                    new NoSuchAlgorithmException(String.format("Hash function not safe for digital signatures: %s", hashAlg));
        };
    }
    public static String hashName(HashType hash) {
        return GetAlgo(hash);
    }

    @Override
    public Unwrapping Primitive(WrappingKeyOperation operation) throws Exception {
        RsaSsaPkcs1ShaKeyFormat format = (RsaSsaPkcs1ShaKeyFormat) operation.GetFormat();
        String hashAlg = hashName(format.hashType);
        Object[] result = RSAHashFunc(hashAlg);
        if (result[2] != null) {
            throw (NoSuchAlgorithmException) result[2];
        }
        RsaSsaPkcs1Sha rsassapkcs1sha = new RsaSsaPkcs1Sha(randomService, (HashFunction) result[0], (int) result[1]);
        return rsassapkcs1sha;
    }
}
