package com.onqlave.connection;

import com.onqlave.contract.RetrySettings;
import com.onqlave.contract.request.OnqlaveRequest;

import java.net.http.HttpClient;
import java.util.Map;

public class ClientImpl implements Client {
    private  HttpClient client;
    private  RetrySettings retrySettings;

    public ClientImpl(HttpClient client, RetrySettings retrySettings) {
        this.client = client;
        this.retrySettings = retrySettings;
    }

    @Override
    public byte[] Post(String resource, OnqlaveRequest body, Map<String, String> header) throws Exception {
        return new byte[0];
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public RetrySettings getRetrySettings() {
        return retrySettings;
    }

    public void setRetrySettings(RetrySettings retrySettings) {
        this.retrySettings = retrySettings;
    }
}
