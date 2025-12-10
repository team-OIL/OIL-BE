package com.example.OIL.global.error.exception;

import org.springframework.http.HttpStatus;

public interface ErrorProperty {

    HttpStatus getStatus();
    String getCode();
    String getMessage();
}
