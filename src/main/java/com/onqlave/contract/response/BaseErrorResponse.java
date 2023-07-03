package com.onqlave.contract.response;

public class BaseErrorResponse {
    private Error Error;

    public Error getError() {
        return Error;
    }

    public void setError(Error Error) {
        this.Error = Error;
    }
}
