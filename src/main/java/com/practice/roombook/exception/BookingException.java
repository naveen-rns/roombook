package com.practice.roombook.exception;

import org.springframework.http.HttpStatus;

public class BookingException extends Exception{
    private HttpStatus status;
    private String errorCode;
    private String errorDesc;

    public BookingException(HttpStatus status, String errorCode, String errorDesc) {
        super(errorDesc);
        this.status = status;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
