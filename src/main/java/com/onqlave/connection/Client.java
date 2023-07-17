package com.onqlave.connection;

import com.onqlave.contract.request.OnqlaveRequest;

import java.util.Map;

public interface Client {
    byte[] post(String resource, OnqlaveRequest body, Map<String, String> header) throws Exception;
}
