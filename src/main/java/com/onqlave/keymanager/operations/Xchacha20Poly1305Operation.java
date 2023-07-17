package com.onqlave.keymanager.operations;

import com.onqlave.types.*;

public class Xchacha20Poly1305Operation implements KeyOperation {
    private KeyFactory factory;
    private Xchacha20KeyFormat format;
    public static final int XCHACHA20_POLY1305_VERSION = 0;
    public static final int XCHACHA20_POLY1305_KEY_SIZE = 32;

    public Xchacha20Poly1305Operation(KeyFactory factory) {
        this.factory = factory;
        this.format = new Xchacha20KeyFormat(XCHACHA20_POLY1305_KEY_SIZE, XCHACHA20_POLY1305_VERSION);
    }

    @Override
    public KeyFormat getFormat() {
        return this.format;
    }

    @Override
    public KeyFactory getFactory() {
        return this.factory;
    }
}
