package com.gbossoufolly.blogapi.exceptions;

public class ApiException extends RuntimeException{

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
