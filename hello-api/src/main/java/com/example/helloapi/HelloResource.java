package com.example.helloapi;

public class HelloResource {

    private String message;

    public HelloResource(String message) {
        this.message = message;
    }

    public HelloResource() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
