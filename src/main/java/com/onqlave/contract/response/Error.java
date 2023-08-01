package com.onqlave.contract.response;

import java.util.List;

public class Error {
    private String Status;
    private String Message;
    private String CorrelationID;
    private List<Object> Details;
    private int Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getCorrelationID() {
        return CorrelationID;
    }

    public void setCorrelationID(String CorrelationID) {
        this.CorrelationID = CorrelationID;
    }

    public List<Object> getDetails() {
        return Details;
    }

    public void setDetails(List<Object> Details) {
        this.Details = Details;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }
}