package com.team.mjusi.util;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    private String message;
    private T data;
    private T other;

    public ApiResponse() {
    }

    public ApiResponse(String message, T data, T other) {
        this.message = message;
        this.data = data;
        this.other = other;
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getOther() {
        return other;
    }

    public void setOther(T other) {
        this.other = other;
    }
}
