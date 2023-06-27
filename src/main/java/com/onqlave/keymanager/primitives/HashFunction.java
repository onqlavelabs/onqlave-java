package com.onqlave.keymanager.primitives;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {
    public static MessageDigest GetHashFunc(String hash) throws NoSuchAlgorithmException {
        return switch (hash) {
            case "SHA1" -> MessageDigest.getInstance("SHA-1");
            case "SHA224" -> MessageDigest.getInstance("SHA-224");
            case "SHA256" -> MessageDigest.getInstance("SHA-256");
            case "SHA384" -> MessageDigest.getInstance("SHA-384");
            case "SHA512" -> MessageDigest.getInstance("SHA-512");
            default -> null;
        };
    }
}
