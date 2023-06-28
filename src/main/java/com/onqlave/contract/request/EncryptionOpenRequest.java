package com.onqlave.contract.request;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EncryptionOpenRequest implements OnqlaveRequest {
    @Override
    public byte[] GetContent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(this);
    }
}
