package com.onqlave.keymanager.operations;

import com.onqlave.types.*;

public class Xchacha20Poly1305Operation implements KeyOperation {
    public static final int XCHACHA20_POLY1305_VERSION = 0;
    public static final int XCHACHA20_POLY1305_KEY_SIZE = 32;
    private KeyFactory factory;
    private Xchacha20KeyFormat format;

    public Xchacha20Poly1305Operation(KeyFactory factory) {
        this.factory = factory;
        this.format = new Xchacha20KeyFormat(XCHACHA20_POLY1305_KEY_SIZE, XCHACHA20_POLY1305_VERSION);
    }

    public KeyFactory getFactory() {
        return factory;
    }

    public void setFactory(KeyFactory factory) {
        this.factory = factory;
    }

    public Xchacha20KeyFormat getFormat() {
        return format;
    }

    public void setFormat(Xchacha20KeyFormat format) {
        this.format = format;
    }


    @Override
    public KeyFormat GetFormat() {
        return null;
    }

    @Override
    public KeyFactory GetFactory() {
        return null;
    }
}
