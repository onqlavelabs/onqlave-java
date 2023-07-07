package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.Unwrapping;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.Base64;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.*;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;

public class RsaSsaPkcs1Sha implements Unwrapping {
    private CPRNGService randomService;
    private MessageDigest hashFunc;
    private int hashID;

    public RsaSsaPkcs1Sha(CPRNGService randomService, MessageDigest hashFunc, int hashID) {
        this.randomService = randomService;
        this.hashFunc = hashFunc;
        this.hashID = hashID;
    }

    @Override
    public byte[] UnwrapKey(byte[] wdk, byte[] epk, byte[] fp, byte[] password) throws Exception {
        PrivateKey privateKey = this.parseEncryptedPKCS8(epk, new String(password));
        RSAPrivateCrtKey k = (RSAPrivateCrtKey) privateKey;
        return decryptOAEP(k, wdk);
    }

    private byte[] decryptOAEP(RSAPrivateCrtKey privateKey, byte[] ciphertext) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(ciphertext);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(decodedBytes);
    }

    public static PrivateKey parseEncryptedPKCS8(byte[] keyData, String password) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] decodedBytes = Base64.getDecoder().decode(keyData);
        PEMParser pemParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(decodedBytes)));
        Object object = pemParser.readObject();

        if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
            PKCS8EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = (PKCS8EncryptedPrivateKeyInfo) object;
            InputDecryptorProvider decryptionProv = new JceOpenSSLPKCS8DecryptorProviderBuilder().build(password.toCharArray());
            PrivateKeyInfo keyInfo = encryptedPrivateKeyInfo.decryptPrivateKeyInfo(decryptionProv);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            PrivateKey key = converter.getPrivateKey(keyInfo);
            return key;
        } else if (object instanceof PEMKeyPair) {
            PEMKeyPair pemKeyPair = (PEMKeyPair) object;
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            return converter.getPrivateKey(pemKeyPair.getPrivateKeyInfo());
        } else {
            throw new Exception("Invalid encrypted private key format.");
        }
    }
}



