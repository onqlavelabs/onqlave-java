package com.onqlave.contract.config;

import javax.xml.datatype.Duration;


public class RetrySettings {
    private   int count;
    private Duration waitTime;
    private Duration maxWaitTime;

    public RetrySettings(int count, Duration waitTime, Duration maxWaitTime) {
        this.count = count;
        this.waitTime = waitTime;
        this.maxWaitTime = maxWaitTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
}
