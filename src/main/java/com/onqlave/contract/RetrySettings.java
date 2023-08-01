package com.onqlave.contract;

import java.time.Duration;


public class RetrySettings {
    private final int maxRetries;
    private final Duration waitTime;
    private final Duration maxWaitTime;

    public RetrySettings(int count, Duration waitTime, Duration maxWaitTime) {
        this.maxRetries = count;
        this.waitTime = waitTime;
        this.maxWaitTime = maxWaitTime;
    }

    public int getMaxRetries() {
        return maxRetries;
    }
}
