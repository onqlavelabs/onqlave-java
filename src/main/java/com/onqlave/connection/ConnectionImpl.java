package com.onqlave.connection;

import com.onqlave.contract.Configuration;
import com.onqlave.contract.request.OnqlaveRequest;
import com.onqlave.utils.Constants;
import com.onqlave.utils.Hasher;

import java.util.HashMap;
import java.util.Map;

public class ConnectionImpl implements Connection {

    private Hasher hasher;
    private Configuration configuration;

    private Client client;

    public ConnectionImpl(Configuration configuration, Hasher hasher, Client client) {
        this.configuration = configuration;
        this.hasher = hasher;
        this.client = client;
    }

    @Override
    public byte[] Post(String resource, OnqlaveRequest body) throws Exception {
        String operation = "POST";

        String url = String.format("%s/%s", this.configuration.getArxURL(), resource);
        String arxUrl = this.configuration.getArxURL();
        String arxId = this.configuration.getArxID();
        String accessKey = this.configuration.getCredential().getAccessKey();
        byte[] content = body.GetContent();
        int contentLength = content.length;
        String digest = hasher.Digest(body);
        Map<String, String> headers = new HashMap<String, String>() {{
            put(Constants.OnqlaveAPIKey, accessKey);
            put(Constants.OnqlaveArx, arxId);
            put(Constants.OnqlaveHost, arxUrl);
            put(Constants.OnqlaveAgent, Constants.ServerType);
            put(Constants.OnqlaveContentLength, String.valueOf(contentLength));
            put(Constants.OnqlaveDigest, digest);
            put(Constants.OnqlaveVersion, Constants.Version);
        }};

        String signature = this.hasher.Sign(headers, this.configuration.getCredential().getSigningKey());

        headers.put(Constants.OnqlaveContentType, "application/json");
        headers.put(Constants.OnqlaveRequestTime, "");
        headers.put(Constants.OnqlaveSignature, signature);
        byte[] response = this.client.Post(url, body, headers);
        return response;
    }
}
