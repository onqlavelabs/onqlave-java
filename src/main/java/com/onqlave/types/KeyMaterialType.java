package com.onqlave.types;

public enum KeyMaterialType {
    KeyMaterialUNKNOWNKEYMATERIAL(0),
    KeyMaterialSYMMETRIC(1),
    KeyMaterialASYMMETRICPRIVATE(2),
    KeyMaterialASYMMETRICPUBLIC(3),
    KeyMaterialREMOTE(4);

    private final int value;

    KeyMaterialType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
