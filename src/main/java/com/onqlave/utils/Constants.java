package com.onqlave.utils;

public class Constants {
    public static final String OnqlaveAPIKey = "ONQLAVE-API-KEY";
    public static final String OnqlaveContentType = "Content-Type";
    public static final String OnqlaveHost = "ONQLAVE-HOST";
    public static final String OnqlaveVersion = "ONQLAVE-VERSION";
    public static final String OnqlaveSignature = "ONQLAVE-SIGNATURE";
    public static final String OnqlaveDigest = "ONQLAVE-DIGEST";
    public static final String OnqlaveArx = "ONQLAVE-ARX";
    public static final String OnqlaveAgent = "User-Agent";
    public static final String OnqlaveRequestTime = "ONQLAVE-REQUEST-TIME";
    public static final String OnqlaveContentLength = "ONQLAVE-CONTEXT-LEN";
    public static final String ServerType = "Onqlave/0.1";
    public static final String Version = "0.1";

    public static final int AES_GCM_IV_SIZE = 12;
    public static final int AES_GCM_TAG_SIZE = 16;
    public static final long aesGCMMaxPlaintextSize = (1L << 36) - 31;
    public static final int intSize = 32 << (~Long.SIZE >> 63);
    public static final int maxInt = (1 << (intSize - 1)) - 1;
    public static final int maxIntPlaintextSize = maxInt - AES_GCM_IV_SIZE - AES_GCM_TAG_SIZE;
    public static final int MIN_NO_IV_CIPHERTEXT_SIZE = AES_GCM_TAG_SIZE;
    public static final int MIN_PREPEND_IV_CIPHERTEXT_SIZE = AES_GCM_IV_SIZE + AES_GCM_TAG_SIZE;


    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";


    public static final int XChaCha20Poly1305NonceSize = 24;
    public static final int XChaCha20Poly1305TagSize = 16;
    public static final int MIN_PREPEND_IV_CIPHERTEXT_SIZE_XCHACHA = XChaCha20Poly1305NonceSize + XChaCha20Poly1305TagSize;
    public static final int MIN_NO_IV_CIPHERTEXT_SIZE_XCHACHA = XChaCha20Poly1305TagSize;


}
