package com.onqlave.contract;

import java.time.Duration;


public class RetrySettings {
    private int maxRetries;
    private Duration waitTime;
    private Duration maxWaitTime;

    public RetrySettings(int count, Duration waitTime, Duration maxWaitTime) {
        this.maxRetries = count;
        this.waitTime = waitTime;
        this.maxWaitTime = maxWaitTime;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setCount(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public Duration getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Duration waitTime) {
        this.waitTime = waitTime;
    }

    public Duration getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Duration maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public void Valid() throws Exception {
        // TODO:
    }
}
