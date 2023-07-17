package com.onqlave.connection;

import com.onqlave.contract.Configuration;
import com.onqlave.contract.request.OnqlaveRequest;
import com.onqlave.utils.Constants;
import com.onqlave.utils.Hasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ConnectionImpl implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Hasher hasher;
    private final Configuration configuration;
    private final Client client;

    public ConnectionImpl(Configuration configuration, Hasher hasher, Client client) {
        this.configuration = configuration;
        this.hasher = hasher;
        this.client = client;
    }

    @Override
    public byte[] post(String resource, OnqlaveRequest body) throws Exception {
        String operation = "POST";

        String url = String.format("%s/%s", this.configuration.getArxURL(), resource);
        String arxUrl = this.configuration.getArxURL();
        String arxId = this.configuration.getArxID();
        String accessKey = this.configuration.getCredential().accessKey();
        byte[] content = body.getContent();
        int contentLength = content.length;
        String digest = hasher.digest(body);
        Map<String, String> headers = new HashMap<>() {{
            put(Constants.OnqlaveAPIKey, accessKey);
            put(Constants.OnqlaveArx, arxId);
            put(Constants.OnqlaveHost, arxUrl);
            put(Constants.OnqlaveAgent, Constants.ServerType);
            put(Constants.OnqlaveContentLength, String.valueOf(contentLength));
            put(Constants.OnqlaveDigest, digest);
            put(Constants.OnqlaveVersion, Constants.Version);
        }};

        String signature = this.hasher.sign(headers, this.configuration.getCredential().signingKey());

        long unixTime = Instant.now().getEpochSecond();
        headers.put(Constants.OnqlaveContentType, "application/json");
        headers.put(Constants.OnqlaveRequestTime, Long.toString(unixTime));
        headers.put(Constants.OnqlaveSignature, signature);
        try {
            return this.client.post(url, body, headers);
        } catch (Exception e) {
            LOGGER.error(String.format("[onqlave] SDK: %s - Failed sending %s request", operation, "HTTP:POST"));
            throw new Exception("cannot connect to server");
        }
    }
}
