package com.onqlave.types;

public enum HashType {
    UNKNOWN(0),
    SHA1(1),
    SHA384(2),
    SHA256(3),
    SHA512(4),
    SHA224(5);

    private final int value;

    HashType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
