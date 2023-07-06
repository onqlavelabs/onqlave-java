package com.onqlave.utils;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.macs.Poly1305;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import static com.onqlave.utils.Constants.XChaCha20Poly1305NonceSize;
import static com.onqlave.utils.Constants.XChaCha20Poly1305TagSize;

public class ChaCha20Poly1305Helper {
    public static byte[] Encrypt(byte[] plaintext, byte[] key, byte[] nonce) throws Exception {
        return new byte[0];
    }

    public static byte[] Decrypt(byte[] cipherText, byte[] key) throws Exception {
        return new byte[0];
    }
}
