package com.example.demo1.response;

public class GeneralResponse {

    String message;

    public GeneralResponse(String message){
        this.message = message;
    }

    public GeneralResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
