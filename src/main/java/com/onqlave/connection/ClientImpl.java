package com.onqlave.connection;

import com.onqlave.contract.RetrySettings;
import com.onqlave.contract.request.OnqlaveRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
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
        String operation = "https";

        HttpRequest.Builder builder = HttpRequest.newBuilder();


        for (var entry : header.entrySet()) {
            builder.setHeader(entry.getKey(), entry.getValue());
        }

        HttpRequest request = builder
                .uri(new URI(resource))
                .POST(HttpRequest.BodyPublishers.ofByteArray(body.GetContent()))
                .build();


        //TODO: should add try/catch here. Or we need to be study how can we throwable in java
        try {
            HttpResponse<byte[]> response = this.executeWithRetry(request);
            return response.body();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private HttpResponse executeWithRetry(HttpRequest request) throws Exception {
        for (int i = 0; i < this.retrySettings.getMaxRetries(); i++) {
            HttpResponse<byte[]> response = this.client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() == 200) {
                return response;
            }
            //TODO: consider to sleep with this.retrySettings.maxWaitTime here
        }
        return null;
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
