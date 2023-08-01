package com.onqlave.connection;

import com.onqlave.contract.RetrySettings;
import com.onqlave.contract.request.OnqlaveRequest;
import com.onqlave.exception.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ClientImpl implements Client {
    private final HttpClient client;
    private final RetrySettings retrySettings;

    private static final Logger LOGGER = LogManager.getLogger();

    public ClientImpl(HttpClient client, RetrySettings retrySettings) {
        this.client = client;
        this.retrySettings = retrySettings;
    }

    @Override
    public byte[] post(String resource, OnqlaveRequest body, Map<String, String> header) throws Exception {
        String operation = "https";
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        for (var entry : header.entrySet()) {
            builder.setHeader(entry.getKey(), entry.getValue());
        }
        HttpRequest request = builder.uri(new URI(resource)).POST(HttpRequest.BodyPublishers.ofByteArray(body.getContent())).build();

        try {
            HttpResponse<byte[]> response = this.executeWithRetry(request);
            return response.body();
        } catch (Exception e) {
            throw new HttpException();
        }
    }

    private HttpResponse executeWithRetry(HttpRequest request) throws Exception {
        try {
            for (int i = 0; i < this.retrySettings.getMaxRetries(); i++) {
                HttpResponse<byte[]> response = this.client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() == 200) {
                    return response;
                }
            }
        } catch (Exception e) {
            throw new HttpException();
        }

        return null;
    }
}
