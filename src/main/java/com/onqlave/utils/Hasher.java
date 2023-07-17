package com.onqlave.utils;

import com.onqlave.contract.request.OnqlaveRequest;

import java.util.Map;

public interface Hasher {
    String digest(OnqlaveRequest body) throws Exception;
    String sign(Map<String, String> headers, String signingKey) throws Exception;
}

