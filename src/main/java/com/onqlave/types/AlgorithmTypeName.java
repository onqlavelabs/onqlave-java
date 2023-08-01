package com.onqlave.types;

import java.util.HashMap;
import java.util.Map;

import static com.onqlave.types.AlgorithmTypeValue.*;

public class AlgorithmTypeName {
    private static final Map<Integer, String> valueMap = new HashMap<>();

    static {
        valueMap.put(0, UNKNOWN_ALGORITHM);
        valueMap.put(1, AES_GCM_128);
        valueMap.put(2, AES_GCM_256);
        valueMap.put(3, XCHA_CHA_20_POLY_1305);
    }

    public static String fromValue(int value) {
        return valueMap.get(value);
    }
}
