package com.onqlave.contract.request;

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
    private String edk;

    public DecryptionOpenRequest(String edk) {
        this.edk = edk;
    }

    @Override
    public byte[] getContent() throws Exception {
       return ObjectToByteConverter.convertObjectToBytes(this);
    }
}

