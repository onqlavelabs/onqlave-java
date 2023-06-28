package com.onqlave.types;

import java.util.Map;

public class OnqlaveStructure {
    private byte[] edk;
    private Map<String, byte[]> embedded;

    public OnqlaveStructure(byte[] edk, Map<String, byte[]> embedded) {
        this.edk = edk;
        this.embedded = embedded;
    }

    public byte[] getEdk() {
        return edk;
    }

    public void setEdk(byte[] edk) {
        this.edk = edk;
    }

    public Map<String, byte[]> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Map<String, byte[]> embedded) {
        this.embedded = embedded;
    }
}

