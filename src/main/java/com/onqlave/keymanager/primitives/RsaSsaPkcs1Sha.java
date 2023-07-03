package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.Unwrapping;
import com.onqlave.utils.Hasher;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.PKCS8EncodedKeySpec;

import static com.onqlave.utils.Constants.CIPHER_ALGORITHM;
import static com.onqlave.utils.Constants.KEY_ALGORITHM;

public class RsaSsaPkcs1Sha implements Unwrapping {

    private CPRNGService randomService;
    private HashFunction hashFunc;
    private int hashID;

    public RsaSsaPkcs1Sha(CPRNGService randomService, HashFunction hashFunc, int hashID) {
        this.randomService = randomService;
        this.hashFunc = hashFunc;
        this.hashID = hashID;
    }

    @Override
    public byte[] UnwrapKey(byte[] wdk, byte[] epk, byte[] fp, byte[] password) throws Exception {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(epk);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey) privateKey;
        return decryptOAEP(rsaPrivateKey, wdk);


    }

    private byte[] decryptOAEP(RSAPrivateCrtKey privateKey, byte[] ciphertext) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(ciphertext);
    }
}



