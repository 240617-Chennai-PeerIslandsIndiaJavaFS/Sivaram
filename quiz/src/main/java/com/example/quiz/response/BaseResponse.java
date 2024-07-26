package com.example.quiz.response;
import org.springframework.stereotype.Component;

@Component
public class BaseResponse<T>{
    private int statusCode;
    private String message;
    private T body;

    public BaseResponse() {
    }


    public BaseResponse(int statusCode, String message, T body) {
        this.statusCode = statusCode;
        this.message = message;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
