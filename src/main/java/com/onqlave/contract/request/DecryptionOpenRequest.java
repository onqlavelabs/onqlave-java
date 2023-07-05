package com.onqlave.contract.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

class ObjectToByteConverter {
    public static byte[] convertObjectToBytes(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json.getBytes();
    }
}

public class DecryptionOpenRequest implements OnqlaveRequest {
    @SerializedName(value = "encrypted_data_key")
    private String EDK;

    public DecryptionOpenRequest(String EDK) {
        this.EDK = EDK;
    }

    public String getEDK() {
        return EDK;
    }

    public void setEDK(String EDK) {
        this.EDK = EDK;
    }

    @Override
    public byte[] GetContent() throws Exception {
       return ObjectToByteConverter.convertObjectToBytes(this);
    }
}

