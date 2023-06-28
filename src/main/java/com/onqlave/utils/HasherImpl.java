package com.onqlave.utils;

import com.onqlave.contract.request.OnqlaveRequest;

import java.util.Map;

public class HasherImpl implements Hasher{
    public HasherImpl() {
    }

    @Override
    public String Digest(OnqlaveRequest body) throws Exception {
        return null;
    }

    @Override
    public String Sign(Map<String, String> headers, String signingKey) throws Exception {
        return null;
    }
}
