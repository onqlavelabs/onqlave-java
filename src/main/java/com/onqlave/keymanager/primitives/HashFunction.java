package com.onqlave.keymanager.primitives;

import com.onqlave.types.HashType;

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

    public static String GetAlgo(HashType value) throws IllegalArgumentException {
        return switch (value.getValue()) {
            case 0 -> "UNKNOWN_HASH";
            case 1 -> "SHA1";
            case 2 -> "SHA384";
            case 3 -> "SHA256";
            case 4 -> "SHA512";
            case 5 -> "SHA224";
            default -> throw new IllegalArgumentException("Invalid hash type value: " + value);
        };
    }
}
