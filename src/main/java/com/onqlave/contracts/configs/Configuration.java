package com.onqlave.contracts.configs;

public class Configuration {
    private Credential credential;
    private RetrySettings retry;
    private String arxURL;
    private boolean debug;

    public Configuration(Credential credential, RetrySettings retry, String arxURL, boolean debug) {
        this.credential = credential;
        this.retry = retry;
        this.arxURL = arxURL;
        this.debug = debug;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public RetrySettings getRetry() {
        return retry;
    }

    public void setRetry(RetrySettings retry) {
        this.retry = retry;
    }

    public String getArxURL() {
        return arxURL;
    }

    public void setArxURL(String arxURL) {
        this.arxURL = arxURL;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
