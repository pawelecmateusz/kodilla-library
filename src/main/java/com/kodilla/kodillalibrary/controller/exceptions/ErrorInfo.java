package com.kodilla.kodillalibrary.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorInfo {

    private String url;
    private final String message;

    public ErrorInfo(HttpServletRequest request, Exception ex) {
        this.url = request.getRequestURL().toString();
        this.message = ex.getMessage();
    }

    public ErrorInfo(HttpServletRequest request, String message) {
        this.url = request.getRequestURL().toString();
        this.message = message;
    }

    public ErrorInfo(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}
