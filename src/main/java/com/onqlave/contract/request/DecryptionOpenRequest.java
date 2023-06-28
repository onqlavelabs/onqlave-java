package com.onqlave.contract.request;

public class DecryptionOpenRequest implements OnqlaveRequest {
    private String EDK;

    public String getEDK() {
        return EDK;
    }

    public void setEDK(String EDK) {
        this.EDK = EDK;
    }

    @Override
    public byte[] GetContent() throws Exception {
        return new byte[0];
    }
}

