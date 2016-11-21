package com.spring.model;

/**
 * Created by Sam on 21/11/2016.
 */
public class Message {

    private String message;
    private boolean status;

    public Message(){}

    public Message(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
