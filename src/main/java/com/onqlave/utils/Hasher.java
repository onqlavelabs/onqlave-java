package com.onqlave.utils;

import com.onqlave.contract.request.OnqlaveRequest;

import java.util.Map;

public interface Hasher {
    String Digest(OnqlaveRequest body) throws Exception;

    String Sign(Map<String, String> headers, String signingKey) throws Exception;
}

