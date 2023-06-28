package com.onqlave.types;

import java.util.HashMap;
import java.util.Map;

public class AlgorithmTypeValue {
    public static final String UNKNOWN_ALGORITHM ="unknown_algorithm";
    public static final String AES_GCM_128 = "aes-gcm-128";
    public static final String AES_GCM_256 = "aes-gcm-256";
    public static final String XCHA_CHA_20_POLY_1305 = "xcha-cha-20-poly-1305";
    public static final String RSA_SSA_PKCS1_2048_SHA256_F4 = "RSA_SSA_PKCS1_2048_SHA256_F4";
    private static final Map<String, Integer> valueMap = new HashMap<>();

    static {
        valueMap.put(UNKNOWN_ALGORITHM, 0);
        valueMap.put(AES_GCM_128, 1);
        valueMap.put(AES_GCM_256, 2);
        valueMap.put(XCHA_CHA_20_POLY_1305, 3);
    }

    public static int fromValue(String value) {
        return valueMap.get(value);
    }

}

