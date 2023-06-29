package com.onqlave.contract.request;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(this);
    }
}

