package com.onqlave.connection;

import com.onqlave.contract.request.OnqlaveRequest;

public interface Connection {
    public byte[]Post(String resource, OnqlaveRequest body) throws Exception;
}
