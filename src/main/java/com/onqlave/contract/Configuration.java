package com.onqlave.contract;

public class Configuration {
    private Credential credential;
    private RetrySettings retry;
    private String arxURL;

    private String arxID;
    private boolean debug;

    public Configuration(Credential credential, RetrySettings retry, String arxURL, boolean debug) {
        this.credential = credential;
        this.retry = retry;
        this.debug = debug;
        this.extractArxUrl(arxURL);
    }
    private void extractArxUrl(String arxURL) {
        int index = arxURL.lastIndexOf("/");
        this.arxURL = arxURL.substring(0, index);
        this.arxID = arxURL.substring(index+1);
    }
    public String getArxID() {
        return arxID;
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