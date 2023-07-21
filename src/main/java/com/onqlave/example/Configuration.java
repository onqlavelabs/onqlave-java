package com.onqlave.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Configuration {
    public  String accessKey;
    public String signingKey;
    public String secretKey;
    public String arxURL;
    public int maxRetries;
    public int waitTime;
    public int maxWaitTime;
    public boolean debug;
}
